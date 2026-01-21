CREATE TABLE IF NOT EXISTS ai_logs (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  conversation_id VARCHAR(64),
  user_id VARCHAR(64),
  question LONGTEXT,
  prompt LONGTEXT,
  model VARCHAR(100),
  raw_answer LONGTEXT,
  sanitized_answer LONGTEXT,
  formatted_answer LONGTEXT,
  usage_prompt_tokens INT NULL,
  usage_completion_tokens INT NULL,
  usage_total_tokens INT NULL,
  created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  INDEX idx_ai_logs_user_time (user_id, created_at),
  INDEX idx_ai_logs_conversation (conversation_id, created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- If the table already existed with smaller column types, force-upgrade them.
-- This is idempotent in MySQL (re-applying the same type is safe).
ALTER TABLE ai_logs
  MODIFY COLUMN question LONGTEXT,
  MODIFY COLUMN prompt LONGTEXT,
  MODIFY COLUMN raw_answer LONGTEXT,
  MODIFY COLUMN sanitized_answer LONGTEXT,
  MODIFY COLUMN formatted_answer LONGTEXT;