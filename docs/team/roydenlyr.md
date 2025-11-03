# Royden Lim Yi Ren - Project Portfolio Page

## Overview
Finsight is a CLI-based application for university students to manage their finances such as income, expense, investment
, and loan.

## Summary of Contributions

### Code contributed

- Code contributed can be found [here](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=roydenlyr&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-09-19T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=).

### Enhancements implemented

Refactored Finsight's persistence layer into a reusable, fault-tolerant storage framework(`Datamanager`), improving data
integrity, modularity, and consistency across all features.

1. **Designed and implemented** the `DataManager` abstraction to handle: 
   1. Directory and file creation
   2. Atomic file replacement to prevent corruption during writes
   3. UTF-8 encoding to ensure cross-platform text compatibility
2. **Developed reusable utilities** `sanitize()` and `unsanitize()` to safely encode delimiters (`|`, `%`) in text-based 
records, ensuring lossless parsing of user-entered data.
3. **Extended all storage classes** (`LoanDataManager`, `ExpenseDataManager`, etc.) to inherit from `DataManager`, 
eliminating duplicate code while preserving domain-specific parsing.
4. **Enhanced fault tolerance** by graceful recovery of corrupted or missing files by returning only valid records 
instead of empty list or halting execution.
5. **Optimised performance** with `appendToFile()` to append records without rewriting the entire file.
6. Implemented **unit tests for all storage modules** using temporary directories (`@TempDir`) to ensure isolation and
repeatability.
   1. Achieved **100% test coverage** for all persistence class, method, line and branch except `DataManager` branch, 
   achieving 83%.

### Contributions to User Guide

Contributed to 'Saving the data' section inclusive of subsections:

- When it saves
- File format
- Backing up your data
- Moving data to another computer
- Resetting data

### Contributions to Developer Guide

Sections contributed include:

- Design
- Implementation (storage)
- Appendix (Non-functional requirements, Glossary)
- Instructions for manual testing (persistence)

UML contributions include `FinSightArchitecture`, `FinsightClassDiagram`, `DataManagerClassDiagram`, `LoadSequenceDiagram`, `WriteToFileSequenceDiagram`,
`AppendToFileSequenceDiagram`.

### Contributions to team-based tasks

- Repackage project structure [#19](https://github.com/AY2526S1-CS2113-T10-1/tp/pull/19)
- Build and release v1.0 [jar](https://github.com/AY2526S1-CS2113-T10-1/tp/releases/tag/v1.0)
- Writing DG overview section including class diagram [#90](https://github.com/AY2526S1-CS2113-T10-1/tp/pull/90)
- Maintained repository hygiene for shared artifacts by adding files to `.gitignore` [#25](https://github.com/AY2526S1-CS2113-T10-1/tp/pull/25/files)
- Build and release v2.0 [jar](https://github.com/AY2526S1-CS2113-T10-1/tp/releases/tag/v2.0)
- Release PDF version of DG and UG [here](https://github.com/AY2526S1-CS2113-T10-1/tp/releases/tag/v2.0)
- Fix all bugs (sequence diagram, clarity) in DG and UG from PE-D [#173](https://github.com/AY2526S1-CS2113-T10-1/tp/pull/173) and [#176](https://github.com/AY2526S1-CS2113-T10-1/tp/pull/176).

### Review/mentoring contributions

- Link to PRs reviewed [here](https://github.com/AY2526S1-CS2113-T10-1/tp/pulls?q=is%3Apr+is%3Aclosed+reviewed-by%3Aroydenlyr+).
- Helped team member resolve their issues [#176](https://github.com/AY2526S1-CS2113-T10-1/tp/pull/176), [#180](https://github.com/AY2526S1-CS2113-T10-1/tp/pull/180), [#181](https://github.com/AY2526S1-CS2113-T10-1/tp/pull/181).
