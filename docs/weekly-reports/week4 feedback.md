# Week 4 Project Feedback by PM/TA

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

### Software Process Quality
| #   | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|-----|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 1   | GitHub repository branch protection rules are fully set up so that people cannot push into main without a pull request approval. (needed for Letter Grade C) | ✅         | Branch protection rules have been set up correctly | Canvas assignment Project: Setup, Project grading rubrics                         |
| 2   | Continuous Integration (CI) is fully set up from the beginning. (needed for Letter Grade B)                                                                  | ✅         | CI pipeline exists in `.github/workflows/main.yml` for `push` and `pull_request` to `main`; README includes build badge. | Canvas assignment Project: Setup, Project grading rubrics                         |
| 3   | The team uses the project management board steadily and frequently, and the description of each task is detailed. (needed for Letter Grade B)                | ✅         | Good format of project board with frequent updates | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.1 | Every functionality-related work item on the management board includes a user story, and optionally one or more use cases.                                   | ⚠️         | User story and use cases for work items are missing | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.2 | The design is documented somewhere, either in the work item description, or in a separate design document.                                                   | ⚠️         | A design folder exists, but `docs/design/README.md` is still a placeholder and lacks concrete design artifacts. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.3 | Task assignments are documented clearly in the management board.                                                                                             | ✅         | Al tasks have assignees properly | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 4   | Each active feature branch has an open draft PR against main.                                                                                                | ✅         | Correct PRs had been raised and merged | Week 4 Wednesday Lecture (Lecture 08)                                             |
| 5   | The team has a “definition of done” (BVA) fully documented for the part of the system that is done. (needed for Letter Grade D)                              | ❌         | `docs/bva/README.md` is template guidance only; class/method-level BVA analysis documents are missing. | Project grading rubrics                                                           |
| 6   | GitHub commit history demonstrates evidence of a TDD/BDD workflow for all the non-UI code. (needed for Letter Grade C)                                       | ✅         | Tests exist for current domain classes and commit history shows well followed TDD/BDD workflow | Project grading rubrics                                                           |

### Planning & Progress Evaluation
| # | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 7 | The team documents every week’s planning and progress evaluation professionally. (needed for Letter Grade B)                                                 | ⚠️         | Weekly report exists (`docs/weekly-reports/report.md`) with Week 3 and Week 4 entries, but includes unfinished placeholder line (`Person: Task`) and template remnants. | Week 4 Wednesday Lecture (Lecture 08), Project grading rubrics                    |

### Progress & Collaboration
| #  | Item                                                                                                                                                             |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|----|------------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 8  | README is updated properly, including the project name, contributors, and build status badge. In addition, the team should specify the GUI library if appliable. | ⚠️         | Project name, contributors, and build badge are present but GUI library is not specified (if planned). | Canvas assignment Project: Setup                                                  |
| 9  | Overall progress on "Game Setup Phase"                                                                                                                           | ⚠️         | Core setup pieces exist (`Game`, `TokenBank`, `Deck`, `Card`, `Noble`) with tests; several domain classes/loaders remain empty (`Player`, `RuleValidator`, `ActionResult`, `CardLoader`, `NobleLoader`, `MessageProvider`). | Canvas assignment Project: Week 4 Guidance                                        |
| 10 | Collaboration: Quality of discussion in PR reviews and work item comments on the board.                                                                          | ✅         | All good based on available notes and board-related assumption. |                                                                                   |

## Additional Comments
- Prioritize completing concrete BVA deliverables (class/method analysis files) and replacing placeholder documentation sections before next evaluation.
- For stronger TDD evidence, keep PRs and commits small and show test-first progression explicitly in commit messages or PR notes.
