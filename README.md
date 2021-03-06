# Pre-work - *SimpleToDoApp*

**SimpleToDoApp** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Alex Feaser**

Time spent: **9** hours spent in total

## User Stories

The following **required** functionality is completed:

* [X] User can **successfully add and remove items** from the todo list
* [X] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [X] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [X] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [X] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [ ] Add support for completion due dates for todo items (and display within listview item)
* [X] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items (Alert.DialogBuilder)
* [X] Add support for selecting the priority of each todo item (and display in listview item)
* [X] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [X] RecyclerView added for increased performance and functionality
* [X] Implements the new Android Jetpack Architecture Component (AAC) design using ViewModel and LiveData
* [X] Short click opens Item for edit
* [X] Swipe an item to delete it
* [X] Long click allows for animated drag repositioning of Items (not persistent)
* [X] Input validation for item creation with button animation and snackbar on failure
* [X] Toggling an item's star in RecyclerView updates the appropriate value in database
* [X] Code duplication minimized for Item creation and editting 
* [X] Star-style checkbox for marking an item as "important"
* [X] Item creation EditText uses AutoComplete, text suggestions, and word capitalization
* [X] Clip-art icon used for FloatingActionButton
* [ ] Add spinner to ActionBar with sorting options
* [ ] Add date members to Item class (created, modified) and getTimeRemaining() method
* [ ] App opens into new 'User select' activity, MainActivity only displays the current User's Items
* [ ] Add checkbox to ActionBar to 'Show items from all users'
* [ ] Dynamically add 'by User' as a sorting option when checked

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://i.imgur.com/emvlwMn.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:** Google's additions to the Java libraries are a significant improvement to vanilla Java.  There is a decent learning curve when it comes to interfacing with XML, R, and Gradle, but these tools are not only incredibly effective, they also provide important insights into structurization and organization of a project.

**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

**Answer:** I skipped this part of the pre-work, as I knew that I would eventually replace ListView with RecyclerView, so I just started with it.  In doing so, I had to create a RecyclerView Adapter, which is of a somewhat higher complexity, but does the same job of *converting data or a collection of data into View-able layout(s)*, thus acting as a "bridge" between a dataset and the UI element in which it will be displayed.

## Notes

This was my first time including Room persistance support in Android.  I personally found the initial setup to be a bit more cumbersome than plain old SQLite, but with Room I have laid the groundwork to implement new and complex relationships more easily than I could with SQLite.   

## License

    Copyright [2018-2019] [Alex Feaser]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
