# User Guide

## Introduction

FinSight is a CLI-based app for managing finances such as income, expenses, loans and investments.

## Quick Start

1. Ensure that you have Java `17` or above installed.
2. Download the latest version of `FinSight` from [here](https://github.com/AY2526S1-CS2113-T10-1/tp).
3. Copy the file to the folder you want to use as the home folder for FinSight.
4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar finsight.jar` command to run the application.
5. Type the command into the terminal and press Enter to execute it. Any input given that is not a correct command will show all possible commands.
6. Refer to the [Features](#features) below for details of each command.

## Features

> [!NOTE]
> * Words in `<UPPER_CASE>` are the parameters to be supplied by the user.<br>
  e.g. in `delete loan <INDEX>`, `<INDEX>` is a parameter which can be used as `delete loan 1`.
> 
> * Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.
> 
> * Parameters must be in exact order.<br>
  e.g. if the command specifies `d/<DESCRIPTION> a/<AMOUNT>`, the exact order must be followed for the command to work.
> 
> * Extraneous parameters for commands that do not take in parameters (such as `list loan`, `list expense`, `list investment`, `list income` and `bye`) will be ignored.<br>
  e.g. if the command specifies `bye 123`, it will be interpreted as `bye`.
> 
> * If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.



### List all loans: `list loan`
Prints the list of all loans onto the terminal with an index starting from 1.

Format: `list loan`

### Add a loan: `add loan`
Adds a loan to the list. The loan will include a description and the amount borrowed as well as the date and time to return the loaned amount by.

Format: `add loan d/<DESCRIPTION> a/<AMOUNT_BORROWED> r/<LOAN_RETURN_DATE_AND_TIME>`

* The `<LOAN_RETURN_DATE_AND_TIME>` must be of format (dd-MM-yyyy HH:mm).
* The `<AMOUNT_BORROWED>` cannot contain punctuation.

Example of usage:

`add loan d/ loan 1 a/ 10000 r/ 10-10-2026 19:00`

`add loan d/ loan 2 a/ 10.56 r/ 11-10-2056 23:59`

### Delete a loan: `delete loan`
Deletes the loan at <INDEX> from the list of loan

Format: `delete loan <INDEX>`

* The `<INDEX>` cannot contain punctuation.

Example of usage:

`delete loan 1`

`delete loan 3`

### Set loan as repaid: `loan repaid`
Sets the loan at <INDEX> as repaid

Format: `loan repaid <INDEX>>`

* The `<INDEX>` cannot contain punctuation.

Example of usage:

`loan repaid 1`

`loan repaid 3`

### List all expenses: `list expense`
Prints the list of all expenses onto the terminal, with starting index of 1

Format: `list expense`

### Add expense: `add expense`
Adds an expense to the list. The expense will include a description and the amount spent.

Format: `add expense d/<DECRIPTION> a/<AMOUNT_SPENT>`

Example of usage: `add expense d/food a/5.50`

### Delete expense: `delete expense`
Deletes the expense from the list.

Format: `delete expense <INDEX>`

* The `<INDEX>` cannot contain punctuation.

Example of usage: `delete expense 2`


### Exit the program: `bye`
Saves all current data and exits the program

Format: `bye`

### Saving the data
FinSight data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: Install the app in the other computer and overwrite the empty data files it creates with the files that contains the data of your previous FinSight home folder.

## Command Summary

| Action             | Format, Examples                                                                                                                     |
|--------------------|--------------------------------------------------------------------------------------------------------------------------------------|
| List Loans         | `list loan`                                                                                                                          |
| Add a Loan         | `add loan d/<DESCRIPTION> a/<AMOUNT_LOANED> r/<LOAN_RETURN_DATE_AND_TIME>`<br/> e.g. `add loan d/ loan1 a/10.55 r/ 10-10-2056 23:59` |
| Delete a Loan      | `delete loan <INDEX>`<br/> e.g. `delete loan 1`                                                                                      |
| Set Loan as Repaid | `loan repaid <INDEX>`<br/> e.g. `loan repaid 1`                                                                                      |
| List Expense       | `list expense`                                                                                                                       |
| Add an Expense     | `add expense d/<DESCRIPTION> a/<AMOUNT_SPEND>`<br/> e.g. `add expense d/food a/6`                                                    |
| Delete An Expense  | `delete expense <INDEX>`<br/> e.g. `delete expense 2`                                                                                |
| Exit Program       | `bye`                                                                                                                                |
