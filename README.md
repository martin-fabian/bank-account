# Before you start

## Requirements

1. Have jdk11 installed. Later versions may work, but we didn't test it for later versions (go ahead and fix it for
   latest LTS version of java :) )
2. Install docker, you are going to need it! If you would like to work for us, or any company using "latest"
   technologies be ready for docker

## Important note

This task is written to cover multiple levels of seniority. Try to do all the tasks you can. If you feel task is above
your level/available time, but you know how to solve then feel free to add comment inside code. The bare minimum is that
app can be compiled with `-DskipTests` property. There may be errors in tasks. Kudos and extra points for you if you
find/fix them. Tasks are written in random order so start anywhere you want. And more importantly some tasks are
probably making no sense.

# Your tasks

The business came with complaint that our client is not happy before Christmas. Our topmost project manager managed to
reduce the number of tasks that needs to be done before you leave for vacation.

* add validation to api layer, or at least make sure user cannot submit negative transaction value
* fetching bank accounts takes too long, this may be due to fact that Subject is always fetched even when it's not
  needed in algorithm
* client noticed that subject given name is never stored in database. Find out why and fix it without any change in API
* loading non-existent subject returns status `400` instead of proper status `404`
* with each newly created subject, bank account should be created as well. Use already implemented `SequenceProvider`
  written by Santa's Dasher.
    * suffix use `SequenceProvider` to give you par of full 4-digit number such as `0300`, `0001`, `2021`
    * for prefix use `PrefixClient` which is calling external service. It may be using old version of API make sure `v2`
      api is called
* `SequenceProviderImpl` does not meet simple java conventions fix it
* there is a missing core functionality where transactions are added to bank accounts. Implement `TransactionService`
  meeting written documentation and write unit tests for this interface which covers all possible scenarios set by
  contract
* client's tool for static analysis is reporting redundant code

* In last effort non-reliable programmer (he was fired) tried to refactor code and added functionality for loan-asking(
  it's purpose is to just mark bankAccount for backoffice to review manually). It didn't go well and there are multiple
  issues and even suspicion that app is not running anymore, please try to analyze dependency injection, transaction
  itself and throw exception if necessary.

# Optional task

* there is a new version of Spring Boot with JDK. Our client thinks migration between versions is hard. Prove him
  wrong !


