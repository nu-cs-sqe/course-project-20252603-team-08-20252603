# Week 5 Project Feedback by PM/TA

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
- Evaluated using local `main` branch snapshot plus public GitHub API metadata (PRs/commits)
- Week 6 scope intentionally ignored per instruction
- For TDD/BDD, emphasis is on non-UI code progression and commit evidence up to current Week 5 deliverables

### Past Feedback
| # | Item                                                                                                 | Status | Reviewer Notes | Source Instructions or Resources |
|---|------------------------------------------------------------------------------------------------------|:------:|----------------|----------------------------------|
| 0 | The team has closed and merged the past Feedback PR(s), indicating that they have read the feedbacl. | ⚠️     | Week 4 feedback-fix PRs were merged, but there is still an open PR titled `Week 4 Feedback` (`#23`), so this is only partially complete. |                                  |

### Software Process Quality
| # | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 1 | Each active feature branch has an open draft PR against main.                                                                                                | ❌         | Open feature PRs are visible, but they are not draft (e.g., `feature/rule-validator` shown as open with `draft=false`). Requirement explicitly asks for open **draft** PRs for active branches. | Week 4 Wednesday Lecture (Lecture 08)                                             |
| 2 | The team has a “definition of done” (BVA) fully documented for the part of the system that is done. (needed for Letter Grade D)                              | ⚠️         | Strong progress: multiple BVA docs now exist for core classes (`Game`, `TokenBank`, `Deck`, `Card`, `Noble`, `Player`, `CardLoader`, `NobleLoader`, `ActionResult`, `MessageProvider`). Gap: `RuleValidator` remains unimplemented and has no BVA/testing yet. | Project grading rubrics                                                           |
| 3 | GitHub commit history demonstrates evidence of a TDD/BDD workflow for all the non-UI code. (needed for Letter Grade C)                                       | ⚠️         | Good evidence for several units (explicit Red/Green-style commit messages for `ActionResult` and `MessageProvider`, plus test-before-implementation sequences in loader/game setup work). Not yet complete for all non-UI code because `RuleValidator` is still pending. | Project grading rubrics                                                           |

### Planning & Progress Evaluation
| # | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 4 | The team documents every week’s planning and progress evaluation professionally. (needed for Letter Grade B)                                                 | ⚠️         | Weekly report includes Week 3 and Week 4 details, but still contains placeholder/template lines and lacks a completed Week 5 planning/progress section. | Week 4 Wednesday Lecture (Lecture 08), Project grading rubrics                    |

### Progress & Collaboration
| # | Item                                                                                                                                                                                   |  Status   | Reviewer Notes      | Source Instructions or Resources                 |
|---|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|--------------------------------------------------|
| 5 | Overall development progress (recall the recommended order is: Game Setup Phase -> One turn of the game -> Multiple turns -> One win condition -> Other win conditions (if applicable) | ⚠️         | Game setup is substantially improved (token bank initialization, player setup state, card/noble loaders, i18n action/result utility). However, one-turn gameplay flow, multi-turn loop behavior, and win-condition logic are not yet implemented in `main` (`RuleValidator` still empty). | Canvas assignment Project: Week 4 and 5 Guidance |
| 6 | Collaboration: Quality of discussion in PR reviews and work item comments on the board.                                                                                                | ✅         | All good! PR activity is frequent with incremental merges and follow-up fixes; collaboration appears active. |                                                  |

### The following items are not checked by the reviewer as they were checked in the previous weeks
But if your team wants the reviewer to check any of these for any reasons, please contact them or the instructor via either email or tagging them in the feedback PR.

| #   | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|-----|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 1   | GitHub repository branch protection rules are fully set up so that people cannot push into main without a pull request approval. (needed for Letter Grade C) | ➖         | Not checked in Week 5; this was already checked in Week 4. | Canvas assignment Project: Setup, Project grading rubrics                         |
| 2   | Continuous Integration (CI) is fully set up from the beginning. (needed for Letter Grade B)                                                                  | ➖         | Not checked in Week 5; this was already checked in Week 4. | Canvas assignment Project: Setup, Project grading rubrics                         |
| 3   | The team uses the project management board steadily and frequently, and the description of each task is detailed. (needed for Letter Grade B)                | ➖         | Not checked in Week 5; this was already checked in Week 4. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.1 | Every functionality-related work item on the management board includes a user story, and optionally one or more use cases.                                   | ➖         | Not checked in Week 5; this was already checked in Week 4. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.2 | The design is documented somewhere, either in the work item description, or in a separate design document.                                                   | ➖         | Not checked in Week 5; this was already checked in Week 4. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.3 | Task assignments are documented clearly in the management board.                                                                                             | ➖         | Not checked in Week 5; this was already checked in Week 4. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |

## Additional Comments
- Major improvement over Week 4: core setup artifacts now have corresponding tests and BVA files, especially for loaders and localization support.
- To strengthen Week 5 quality further, prioritize: (1) completing `RuleValidator` and related tests/BVA, (2) documenting Week 5 planning/progress cleanly in the weekly report, and (3) using draft PR state consistently for in-progress feature branches.
