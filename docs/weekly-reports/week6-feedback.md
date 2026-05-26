# Week 6 Project Feedback by PM/TA

**Dedicated PM/TA**: Vandan Agrawal (vandanagrawal@u.northwestern.edu)

## How to Read This Feedback
> [!NOTE]
> **Purpose.** This feedback focuses on your team's progress and collaboration. It is meant as guidance, not judgement.

> [!IMPORTANT]
> **Scope.** For the BVA and TDD items, the PM/TA evaluates only the `main` branch. Ongoing work in feature branches will be evaluated after it is merged. If you'd like early feedback on work in progress, please reach out to your PM/TA directly.

> [!TIP]
> **Mistakes are expected :).** As the instructor mentioned in class, early mistakes are part of the learning process. As long as your team addresses the issues after you get the feedback, your grade will not suffer from them.

## Checklist
Status:
- ✅: All done/Good job!
- ⚠️: Attention needed
- ❌: Significant issue found
- ➖: No basis to evaluate

### Review Basis
- Evaluated using the local `main` branch snapshot plus public GitHub API metadata (pull requests).
- Static analysis items (Checkstyle, SpotBugs) are judged from `build.gradle.kts`, repository config files, and CI workflow only; a project-board “to-do” cannot be verified from the repo alone.

### Past Feedback
| # | Item                                                                                                 | Status | Reviewer Notes | Source Instructions or Resources |
|---|------------------------------------------------------------------------------------------------------|:------:|----------------|----------------------------------|
| 0 | The team has closed and merged the past Feedback PR(s), indicating that they have read the feedback. | ✅     | `Week 5 Feedback` was merged into `main` (PR `#40`, merged 2026-05-10). |                                  |

### Software Process Quality
| # | Item                                                                                                                                         | Status | Reviewer Notes | Source Instructions or Resources                                                                              |
|---|----------------------------------------------------------------------------------------------------------------------------------------------|:------:|----------------|---------------------------------------------------------------------------------------------------------------|
| 1 | Checkstyle: Checkstyle is set up or there is a to-do item planned in the Project Management board for this task. (needed for Letter Grade B) | ⚠️     | No Checkstyle Gradle plugin, no `checkstyle.xml` (or similar) in the repo, and CI only runs `./gradlew build`. If a board to-do exists, it is not evidenced here—please add build integration or document the plan where reviewers can see it. | Week 6 Monday lecture (Lecture 11); the build script and config file in the repository for Lab: Code Coverage |
| 2 | SpotBugs: SpotBugs is set up or there is a to-do item planned in the Project Management board for this task. (needed for Letter Grade B)     | ⚠️     | No SpotBugs Gradle plugin or SpotBugs config in the repo; CI does not run SpotBugs. Same note as Checkstyle regarding verifiable board planning. | Week 6 Monday lecture (Lecture 11); the build script and config file in the repository for Lab: Code Coverage |

### Planning & Progress Evaluation
| # | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 4 | The team documents every week’s planning and progress evaluation professionally. (needed for Letter Grade B)                                                 | ⚠️         | `docs/weekly-reports/report.md` still has placeholder/template lines and no completed Week 5 or Week 6 section in the copy under review; tighten this before the next milestone. | Week 4 Wednesday Lecture (Lecture 08), Project grading rubrics                    |

### Progress & Collaboration
| # | Item                                                                                                                                                                                   |  Status   | Reviewer Notes      | Source Instructions or Resources                 |
|---|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|--------------------------------------------------|
| 5 | Overall development progress (recall the recommended order is: Game Setup Phase -> One turn of the game -> Multiple turns -> One win condition -> Other win conditions (if applicable) | ⚠️         | **Major update on `main`:** `RuleValidator` is implemented (player count + take-token rules) with BVA in `docs/bva/RuleValidator.md`; `Game` wires validation, loads **90 cards / 10 nobles**, exposes `takeTokens` (updates bank/player and advances `currentPlayerIndex`), and the app is runnable via `./gradlew run` (`application` plugin, `ConsoleGame`). **Merged** PR `#42` (“First round finish”) matches the Week 6 report. **Still ahead:** buy/reserve flows, noble visits, prestige/win detection—tracked in part by open **draft** PR `#44` (“Player reserve card”). | Canvas assignment Project: Week 4 and 5 Guidance |

### The following items are not checked by the reviewer this week as they were checked in the previous weeks
If your team wants the reviewer to check any of these for any reasons, please contact them or the instructor via either email or tag/mention them in the feedback PR.

| #   | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|-----|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 1   | GitHub repository branch protection rules are fully set up so that people cannot push into main without a pull request approval. (needed for Letter Grade C) | ➖         | Not checked in Week 6; this was already checked in prior weeks (e.g., Week 4/5 scope). | Canvas assignment Project: Setup, Project grading rubrics                         |
| 2   | Continuous Integration (CI) is fully set up from the beginning. (needed for Letter Grade B)                                                                  | ➖         | Not checked in Week 6; this was already checked in prior weeks (e.g., Week 4/5 scope). | Canvas assignment Project: Setup, Project grading rubrics                         |
| 3   | The team uses the project management board steadily and frequently, and the description of each task is detailed. (needed for Letter Grade B)                | ➖         | Not checked in Week 6; this was already checked in prior weeks (e.g., Week 4/5 scope). | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.1 | Every functionality-related work item on the management board includes a user story, and optionally one or more use cases.                                   | ➖         | Not checked in Week 6; this was already checked in prior weeks (e.g., Week 4/5 scope). | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.2 | The design is documented somewhere, either in the work item description, or in a separate design document.                                                   | ➖         | Not checked in Week 6; this was already checked in prior weeks (e.g., Week 4/5 scope). | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.3 | Task assignments are documented clearly in the management board.                                                                                             | ➖         | Not checked in Week 6; this was already checked in prior weeks (e.g., Week 4/5 scope). | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 4   | Each active feature branch has an open draft PR against main.                                                                                                | ➖         | Not checked in Week 6; this was already checked in prior weeks (e.g., Week 4/5 scope). | Week 4 Wednesday Lecture (Lecture 08)                                             |
| 5   | The team has a “definition of done” (BVA) fully documented for the part of the system that is done. (needed for Letter Grade D)                              | ➖         | Not checked in Week 6; this was already checked in prior weeks (e.g., Week 4/5 scope). | Project grading rubrics                                                           |
| 6   | GitHub commit history demonstrates evidence of a TDD/BDD workflow for all the non-UI code. (needed for Letter Grade C)                                       | ➖         | Not checked in Week 6; this was already checked in prior weeks (e.g., Week 4/5 scope). | Project grading rubrics                                                           |
| 7   | Collaboration: Quality of discussion in PR reviews and work item comments on the board.                                                                      | ➖         | Not checked in Week 6; this was already checked in prior weeks (e.g., Week 4/5 scope). |                                                                                   |

## Additional Comments
- The above reflects the updated tree (e.g. `RuleValidator`, expanded `Game`, `docs/bva/RuleValidator.md`, `application` + `run` in `build.gradle.kts`, and filled Week 5–6 weekly report sections).
- **Week 6 rubric gap:** Checkstyle and SpotBugs are still **not** integrated in Gradle or CI—this remains the highest-priority Week 6 process item.
- **Process:** Open PR `#45` (“Feedback Week 6”) is for your team to read and merge per course workflow; open **draft** `#44` continues reserve-card work—good use of draft state.
