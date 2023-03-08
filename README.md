# MilitaryJet

This repo showcases my unopinionated production-grade way of automatically testing Jetpack Compose UIs. 
EVERY single UI component, their possible behaviours and states were all tested!

- With this repo, you can perform any kind of automated Jetpack Compose UI testing - i.e. you can test 
ANYTHING (both visible and invisible (i.e. things that are not visible on the UI but exists in the 
node - e.g. content description)) on the UI. For example, you can test whether or not a TextField's 
trailing icon and/or it's content description displays/exists on the UI.
- If there is anything that's not covered that you want to test, you can be able to create custom 
semantics in order to test ANYTHING on a Jetpack Compose UI!
- So, READY! SET!! [GO](https://github.com/emperorfin/MilitaryJet/tree/master/app/src/androidTest)!!!

## Project Tech-stack and Characteristics

* Android Framework
* Kotlin
* Jetpack Compose
* Jetpack Compose Material Design 3 Components
* Jetpack Compose Material Design Extended Icons
* ViewModel
* Kotlin Coroutine
* StateFlow
* Reactive Programing
* UI Testing and Individual UI Component Testing
* Architecting Jectpack Compose UI Tests
* Composable Previews
* Extension Functions
* Custom semantics (for testing almost anything on the UI such as for asserting whether the leading 
icon of a TextField is displayed)

## Todo

 - [ ] Screenshot testing using Paparazzi including CI setup
 - [ ] More...

## Important Note
- Always check and use the highest numbered version of a test case function as that is, in my opinion, 
the recommended one to use. But you can choose which ever version of the test case function you preferred.
- For a more structured way of testing, it's highly recommended to use the test classes with the 
highest number suffixed on the file/class name.

## Why MilitaryJet?

The name MilitaryJet has NO connection whatsoever with a military/army/jet fighters. The "Jet" in 
MilitaryJet is from JETpack Compose and "Military" in MilitaryJet is from MILITARY-grade from this 
repo's description which means the level of fight against computer software bugs. And then the 
MilitaryJet is the level of weaponry in the fight against Jetpack Compose UI bugs.

## Annoyance Alert

- Too many comments. For example, some comments are just for sample purposes for someone who might 
be interested in a particular test case.


