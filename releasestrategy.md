1. Update build source and push to main
2. Create release/* branch from main and push to git
3. CI for build will trigger
4. Move existing java doc to version folder
5. Generate updated java doc
6. Commit with release version tag in main branch
7. Create release/* branch from main and push to git
8. CI for GitHub pages will trigger
9. Release will trigger with the latest artifact

Note: main branch latest commit should have <release-tag>.
based on tag pattern release will be creted