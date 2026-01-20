@echo off
echo ========================================
echo Push project to new repository
echo ========================================
echo.

if "%1"=="" (
    echo Usage: push-to-new-repo.bat ^<repository-url^>
    echo Example: push-to-new-repo.bat https://github.com/username/repo-name.git
    echo.
    echo Please provide the repository URL:
    set /p REPO_URL="Repository URL: "
) else (
    set REPO_URL=%1
)

echo.
echo Adding remote repository: %REPO_URL%
git remote add new-origin %REPO_URL%

if errorlevel 1 (
    echo.
    echo Remote already exists. Removing old remote...
    git remote remove new-origin
    git remote add new-origin %REPO_URL%
)

echo.
echo Current branch: 
git branch --show-current

echo.
echo Pushing to new repository...
git push -u new-origin master

if errorlevel 1 (
    echo.
    echo Trying with 'main' branch instead...
    git branch -M main
    git push -u new-origin main
)

echo.
echo ========================================
echo Done! Your project has been pushed.
echo ========================================
pause
