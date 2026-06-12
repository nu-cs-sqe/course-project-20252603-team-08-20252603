# Week 3 (04/13/2026-04/19/2026)
**Planning and Progress Tracking**:
1. [done] Mengxi-Xia(Mengxi Xia): Initialized the repository and established early Continuous Integration (CI). (https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/1, https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/2)
2. [done] avemujicasaki(Sicheng Liu): Verified branch protection through Pull Request (PR) approvals and merges. (https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/2, https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/3)
3. [done] zwu454(Zhixuan Wu): Fix badge formatting in README.md. (https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/4)
4. [not started] Team: Draft the "Definition of Done" (BVA) for upcoming game logic; Initiate TDD/BDD workflow for core mechanics implementation.

# Week 4 (04/20/2026-04/26/2026) 
**Planning and Progress Tracking**:
1. [done] Mengxi-Xia(Mengxi Xia): Creating skeleton classes, implementing Game, TokenBank and TokenColor initialization and TDD testing. Link to PR: (https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/11, https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/10, https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/7, https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/8)
2. [done] avemujicasaki(Sicheng Liu): Implementing card, deck and noble initialization logic, and passed all tests with BVA analysis.Link to PR: (https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/9)


# Week 5 (04/27/2026-05/03/2026)
1. [done] Mengxi-Xia(Mengxi Xia): Added TokenBank integration into game setup and implemented Player initial state with full TDD coverage. Link to PR: (https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/25, https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/24)
2. [done] avemujicasaki(Sicheng Liu): Implementing CardLoader class that enable loading cards from external json files. Creating BVA test and method for loading card and noble. Link to PR: (https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/27, https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/26)
3. [done] zwu454(Zhixuan Wu): Implement ActionResult and MessageProvider with i18n support. Link to PR: (https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/36)
4. [done] zwu454(Zhixuan Wu): Implement RuleValidator and integrate with Game.startGame. Link to PR: (https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/39)

# Week 6 (05/04/2026-05/10/2026)
1. [done] Mengxi-Xia(Mengxi Xia): Implement UI and logic for starting the first round. Add 90 cards and 10 nobel cards. Now the code can be run using ./gradle run to start the first round. Fixed Week 5 code based on feedback. Link to PR: (https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/42)

# Week 7 (05/11/2026-05/17/2026)
1. [done] Mengxi-Xia(Mengxi Xia): Implement player reserve card, game engine deal with player reserve card and card reservation rule validator. Link to PR: (https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/44)

# Week 8 (05/17/2026-05/24/2026)
1. [done] Mengxi-Xia(Mengxi Xia): Add jacoco and pitest. Implement player buy card UI and game logic. Add spotbug and checkstyle, fix checkstyle problem and some spotbug problem. Link to PR: (https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/48, https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/47, https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/50)

# Week 9 (05/25/2026-05/31/2026)
1. [done] Mengxi-Xia(Mengxi Xia): Add player buy reserve card, player get noble automatically, winning logic and game over logic. Link to PR: (https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/52)

# Week 10, 11 (06/01/2026-06/13/2026)
1. [done] zwu454(Zhixuan Wu): Completed Clean Code refactoring for long functions across Game and ConsoleGame classes to resolve "Too Big Functions" issue, extracting helper methods and eliminating code duplication. Link to PR: (https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/64)
2. [done] zwu454(Zhixuan Wu): Added order change verification for Deck#shuffle unit test in DeckTest.java to kill the related shuffle mutant and ensure true randomness logic. Link to PR: (https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/66)
3. [done] zwu454(Zhixuan Wu): Updated README.md documentation by incorporating final test coverage statistics, test exemption notes for the UI layer, and the official game rules hyperlink. Link to PR: (https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/68)
4. [done] avemujicasaki(Sicheng Liu): Reached **100% domain JaCoCo branch coverage** (242/242) and **100% domain PIT** line/mutation/test strength (264/264) via BVA-first testing: updated `docs/bva/` and `docs/bva/coverage/` (including Phase 3 plan and reference patch), then added Phase 1–3 tests in `GameTest`, `RuleValidatorTest`, and `MessageProviderTest` (CC-GAME-01 … CC-MSG-02), one commit per case. Link to PR: (https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/70)
5. [done] avemujicasaki(Sicheng Liu): Merged `main` into the coverage branch, resolved `DeckTest` shuffle conflict, and fixed CI checkstyle/SpotBugs issues so the PR passes Gradle build. Link to PR: (https://github.com/nu-cs-sqe/course-project-20252603-team-08-20252603/pull/70)

# Week X (XX/XX/2026-XX/XX/2026) TEMPLATE (You can change the format to whatever the team likes better)
**Planning and Progress Tracking**:
1. [done] Person: Task (Links to PR)
2. [not started] Person: Task (Links to PR)
3. [80% done] Person: Task (Links to PR)
