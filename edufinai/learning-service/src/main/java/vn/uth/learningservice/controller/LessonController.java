package vn.uth.learningservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import vn.uth.learningservice.dto.request.LessonCreateReq;
import vn.uth.learningservice.dto.request.LessonUpdateReq;
import vn.uth.learningservice.dto.response.LessonRes;
import vn.uth.learningservice.mapper.LessonMapper;
import vn.uth.learningservice.model.Creator;
import vn.uth.learningservice.model.Lesson;
import vn.uth.learningservice.service.CreatorService;
import vn.uth.learningservice.service.LessonService;
import vn.uth.learningservice.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lessons")
@Slf4j
public class LessonController {

    private final LessonService lessonService;
    private final CreatorService creatorService;
    private final LessonMapper lessonMapper;
    private final ObjectMapper objectMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<LessonRes>> findAll() {
        List<Lesson> lessons = lessonService.listAll();
        return ResponseEntity.ok(mapToResList(lessons));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonRes> findById(@PathVariable("id") UUID id) {
        Lesson lesson = lessonService.getById(id);
        return ResponseEntity.ok(lessonMapper.toRes(lesson, objectMapper));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<LessonRes> findBySlug(@PathVariable("slug") String slug) {
        Lesson lesson = lessonService.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found with slug: " + slug));
        return ResponseEntity.ok(lessonMapper.toRes(lesson, objectMapper));
    }

    @GetMapping("/tags/{tag}")
    public ResponseEntity<List<LessonRes>> findByTag(@PathVariable("tag") Lesson.Tag tag) {
        List<Lesson> lessons = lessonService.listByTag(tag);
        return ResponseEntity.ok(mapToResList(lessons));
    }

    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<List<LessonRes>> findByDifficulty(@PathVariable("difficulty") Lesson.Difficulty difficulty) {
        List<Lesson> lessons = lessonService.listByDifficulty(difficulty);
        return ResponseEntity.ok(mapToResList(lessons));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<LessonRes>> findByStatus(@PathVariable("status") Lesson.Status status) {
        List<Lesson> lessons = lessonService.listByStatus(status);
        return ResponseEntity.ok(mapToResList(lessons));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ROLE_CREATOR')")
    public ResponseEntity<LessonRes> createLesson(
            @Valid @RequestBody LessonCreateReq request) {
        try {
            log.info("Creating lesson with title: {}", request.getTitle());
            log.debug("Request data: title={}, difficulty={}, tags={}, durationMinutes={}", 
                    request.getTitle(), request.getDifficulty(), request.getTags(), request.getDurationMinutes());
            
            // Step 1: Get user info
            log.info("Step 1: Getting user info from auth-service");
            var userInfo = userService.getMyInfo();
            if (userInfo == null || userInfo.getId() == null) {
                log.error("User info is null or missing ID");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .build();
            }
            log.info("Step 1 completed: User info retrieved, creator ID: {}", userInfo.getId());
            
            // Step 2: Get or create creator
            log.info("Step 2: Getting or creating creator");
            UUID creatorId = userInfo.getId();
            Creator creator = creatorService.getOrCreate(creatorId);
            log.info("Step 2 completed: Creator retrieved/created: {}", creator.getId());

            // Step 3: Map request to entity
            log.info("Step 3: Mapping request to entity");
            Lesson lesson;
            try {
                lesson = lessonMapper.toEntity(request, objectMapper);
                log.info("Step 3 completed: Entity mapped successfully");
            } catch (Exception e) {
                log.error("Step 3 failed: Error mapping request to entity: {}", e.getMessage(), e);
                throw new RuntimeException("Failed to map lesson data: " + e.getMessage(), e);
            }
            
            lesson.setCreator(creator);
            log.debug("Creator set on lesson");

            // Step 4: Generate slug
            log.info("Step 4: Generating slug");
            String slug = Lesson.slugify(request.getTitle());
            String finalSlug = slug;
            int counter = 1;
            while (lessonService.existsBySlug(finalSlug)) {
                finalSlug = slug + "-" + counter;
                counter++;
            }
            lesson.setSlug(finalSlug);
            log.info("Step 4 completed: Lesson slug generated: {}", finalSlug);

            // Step 5: Set timestamps and status
            log.info("Step 5: Setting timestamps and status");
            LocalDateTime now = LocalDateTime.now();
            lesson.setCreatedAt(now);
            lesson.setUpdatedAt(now);
            lesson.setStatus(Lesson.Status.DRAFT);
            log.info("Step 5 completed: Timestamps and status set");

            // Step 6: Validate lesson before save
            log.info("Step 6: Validating lesson");
            if (lesson.getTitle() == null || lesson.getTitle().isBlank()) {
                throw new IllegalArgumentException("Lesson title cannot be blank");
            }
            if (lesson.getContent() == null || lesson.getContent().isBlank()) {
                throw new IllegalArgumentException("Lesson content cannot be blank");
            }
            if (lesson.getDifficulty() == null) {
                throw new IllegalArgumentException("Lesson difficulty cannot be null");
            }
            if (lesson.getTags() == null || lesson.getTags().isEmpty()) {
                throw new IllegalArgumentException("Lesson must have at least one tag");
            }
            log.info("Step 6 completed: Lesson validation passed");

            // Step 7: Save lesson
            log.info("Step 7: Saving lesson to database");
            Lesson savedLesson;
            try {
                savedLesson = lessonService.create(lesson);
                log.info("Step 7 completed: Lesson saved successfully with ID: {}", savedLesson.getId());
            } catch (Exception e) {
                log.error("Step 7 failed: Error saving lesson to database: {}", e.getMessage(), e);
                if (e.getCause() != null) {
                    log.error("Root cause: {}", e.getCause().getMessage(), e.getCause());
                }
                throw new RuntimeException("Failed to save lesson: " + e.getMessage(), e);
            }
            
            // Step 8: Map to response
            log.info("Step 8: Mapping lesson to response");
            LessonRes response;
            try {
                response = lessonMapper.toRes(savedLesson, objectMapper);
                log.info("Step 8 completed: Response mapped successfully");
            } catch (Exception e) {
                log.error("Step 8 failed: Error mapping lesson to response: {}", e.getMessage(), e);
                throw new RuntimeException("Failed to map lesson response: " + e.getMessage(), e);
            }
            
            log.info("Lesson creation completed successfully. Lesson ID: {}", savedLesson.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (IllegalStateException e) {
            // Lỗi khi gọi auth-service
            log.error("Error calling auth-service: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        } catch (IllegalArgumentException e) {
            // Validation error
            log.error("Validation error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .build();
        } catch (RuntimeException e) {
            // Lỗi mapping hoặc database
            log.error("Runtime error creating lesson: {}", e.getMessage(), e);
            if (e.getCause() != null) {
                log.error("Caused by: {}", e.getCause().getMessage(), e.getCause());
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        } catch (Exception e) {
            // Lỗi khác
            log.error("Unexpected error creating lesson: {}", e.getMessage(), e);
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @PutMapping("/{lessonId}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_CREATOR')")
    public ResponseEntity<LessonRes> updateLesson(
            @PathVariable("lessonId") UUID lessonId,
            @Valid @RequestBody LessonUpdateReq request) {

        Lesson existing = lessonService.getById(lessonId);
        var userInfo = userService.getMyInfo();
        UUID userId = userInfo.getId();

        if (!existing.getCreator().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        lessonMapper.patch(existing, request, objectMapper);
        existing.setStatus(Lesson.Status.DRAFT);
        Lesson updated = lessonService.save(existing);

        return ResponseEntity.ok(lessonMapper.toRes(updated, objectMapper));
    }

    @PutMapping("/slug/{slug}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_CREATOR')")
    public ResponseEntity<LessonRes> updateLessonBySlug(
            @PathVariable("slug") String slug,
            @Valid @RequestBody LessonUpdateReq request) {

        Lesson existing = lessonService.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found with slug: " + slug));

        var userInfo = userService.getMyInfo();
        UUID userId = userInfo.getId();

        if (!existing.getCreator().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        lessonMapper.patch(existing, request, objectMapper);
        existing.setStatus(Lesson.Status.DRAFT);
        Lesson updated = lessonService.save(existing);

        return ResponseEntity.ok(lessonMapper.toRes(updated, objectMapper));
    }

    @PutMapping("/{lessonId}/submit")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_CREATOR')")
    public ResponseEntity<LessonRes> submitLesson(
            @PathVariable("lessonId") UUID lessonId) {

        Lesson lesson = lessonService.getById(lessonId);
        var userInfo = userService.getMyInfo();
        UUID userId = userInfo.getId();

        if (!lesson.getCreator().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Lesson submitted = lessonService.submitLesson(lessonId);
        return ResponseEntity.ok(lessonMapper.toRes(submitted, objectMapper));
    }

    @PutMapping("/slug/{slug}/submit")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_CREATOR')")
    public ResponseEntity<LessonRes> submitLessonBySlug(
            @PathVariable("slug") String slug) {

        Lesson lesson = lessonService.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found with slug: " + slug));

        var userInfo = userService.getMyInfo();
        UUID userId = userInfo.getId();

        if (!lesson.getCreator().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Lesson submitted = lessonService.submitLesson(lesson.getId());
        return ResponseEntity.ok(lessonMapper.toRes(submitted, objectMapper));
    }

    @DeleteMapping("/{lessonId}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_CREATOR')")
    public ResponseEntity<Void> deleteLesson(
            @PathVariable("lessonId") UUID lessonId) {

        Lesson lesson = lessonService.getById(lessonId);
        var userInfo = userService.getMyInfo();
        UUID userId = userInfo.getId();

        if (!lesson.getCreator().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        lessonService.delete(lessonId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/slug/{slug}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_CREATOR')")
    public ResponseEntity<Void> deleteLessonBySlug(
            @PathVariable("slug") String slug) {

        Lesson lesson = lessonService.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("Lesson not found with slug: " + slug));

        var userInfo = userService.getMyInfo();
        UUID userId = userInfo.getId();

        if (!lesson.getCreator().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        lessonService.delete(lesson.getId());
        return ResponseEntity.noContent().build();
    }

    private List<LessonRes> mapToResList(List<Lesson> lessons) {
        return lessons.stream()
                .map(lesson -> lessonMapper.toRes(lesson, objectMapper))
                .collect(Collectors.toList());
    }
}
