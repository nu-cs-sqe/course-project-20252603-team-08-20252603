# Instructor Code Review Feedback

**Contact**: Dr. Yiji Zhang (yiji.zhang@northwestern.edu)

**Purpose of This Document**:
The instructor will perform code review with respect to software design, error handling, format and style on the main branch every week starting Week 6 using the letter grade A standards.
The following chapters of the textbook are considered: Chapter 1, 2, 3, 4, 5, 6, 7, 9, and 10. The corresponding lectures are considered, too.

Please note that this feedback does not include evaluation of your progress, the proper use of linters, the quality of your test cases, or your compliance of TDD/BDD workflow.  
You can find the weekly feedback from your dedicated PM/TA for that.

## Week 6 Code Review
I have read every line of production code currently in the main branch. Here are 2 suggestions: 

1. CardLoader class and NobleLoader class: there is a try block but there is no catch. I could have missed some but I can see the following possibilities and they are worth catching and dealt with.
    - JsonParser.parseReader throws com.google.gson.JsonIOException, com.google.gson.JsonSyntaxException
    - TokenColor.valueOf throws IllegalArgumentException
2. Use of magic number in the Game class (2 and 4) and TokenBank class should be avoided.

Otherwise, looks good!

Look forward to reviewing more of your domain logic in the next review.

Please approve and merge the PR once the team has read the feedback. Thanks!