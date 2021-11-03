![Tuwaiq Academy Logo](https://camo.githubusercontent.com/37ca472e2afb74974a0314d89af8f470422a79582bed0d188f9927777230195d/68747470733a2f2f6c61756e63682e73612f6173736574732f696d616765732f6c6f676f732f7475776169712d61636164656d792d6c6f676f2e737667)

# Android Capstone 1 - Tuwaiq Academy 


## Table of contents 📄
- [Overview](#overview)
- [Used Technologies](#used-technologies-⚙)
- [User Sories](#user-sories-👥)
- [Wireframe](#wireframe-📱)
- [Planning and Development](#planning-and-development-🚀)
- [Favorite Functions Work](#favorite-functions-work-💕)
- [Final App](#final-app-🎉)


<hr>

## Overview

People may feel overwhelmed by the large number of tasks they must complete each day. And, every now and then, we forget to do important things or meet important deadlines. The TODO List app is one way to organize your life.
This project is part of my training at Tuwaiq Bootcamp and has the following goals:
- Creating an Android app without a starting codebase
- Create an application based on a specification provided by someone else. 
- Create a dynamic, production-ready application.

<hr>

## Used Technologies ⚙

- ViewModel Architecture 
- Room Database
- Recycler view
- Fragments
- Notification
- Intents
- Data binding

### Tools 
- Git & Github
- Android Studio
- Figma 

<hr>

## User Sories 👥

'TODO List' app user stories:

- As an app user, I want to be able to organize multiple tasks in one place so that I can properly plan my time.
- As an app user, I want to see my task list so that I can get a better idea of what I need to do before I start my day.
- As an app user, I want to  be able to edit my tasks so that I can reschedule them or add more information, giving me more control over how I manage my tasks.
- As an app user, I want to be able to delete my tasks so that I can get rid of the ones that aren't necessary and feel more organized.
- As an app user, I'd like to set a deadline for my tasks so that I know how to prioritize them before I start working.
- As an app user, I'd like to see my overdue tasks so that I can reschedule or delete them as needed.
- As an app user, I want to add more details to my tasks so that I can better understand them, add necessary information (e.g. address, location,...etc.), and understand what each task may entail.
- As an app user, I want to be notified of my upcoming tasks so that I can plan ahead of time.
- As an app user, I want to be able to cross off completed tasks so that I can see what tasks remain.
- As an app user, I want like to be able to filter my tasks based on their due date so that I can focus more on today's tasks while still being aware of what's coming up.


<hr> 

## Wireframe 📱

For the wireframe I used Figma, you can preview my work [here](https://www.figma.com/file/AYhjXaodVIYAIjvc2faNkX/To-Do-List-App---Capstone-1)

Here is part of my design frams: 

![Wireframe](https://e.top4top.io/p_2133wizmf1.png)

<hr> 

## Planning and Development 🚀

Every good appliction need a plan to meet the user needs the app specifications/requirements. My plan was as follows:

1. Brain storming and wirefram design.
2. Front-end: XML design in Android Studio.
3. Back-end:
    - Database schema.
    - Functionality implementation.
4. Testing

To get a better understanding of the app's use and functionalities, I browsed some designs on Pinterest and other to-do list apps in the App Store during the brainstorming stage. In the XML design step, I tried to get as close to my Figma design as possible.

During the back-end implementation, I faced difficulty sectioning the user tasks based on their due date (if it was provided). In the beginning, I tried to implement it using one recycler view, segmenting the tasks based on certain conditions, and controlling the visibility of the task status above each card. The solution was applicable but not the easiest to implement because I encountered numerous errors and problems along the way. Furthermore, during my search, I discovered another way to implement the section concept, which was the Sectioned RecyclerView (Nested recycler views)

The **Nested Recyclerviews** is composed of two recycler views. One we refer to it as the parent recycler, and the other is the child recycler. 

Finally, after completing the requirements, I began testing my app with various use cases to ensure that it functioned properly.

<br>

### Problem Solving Strategy

I mostly solve the problems in the following order: 
1. Identify the problem 
2. Debugging the code
3. Search about the error (Google, Stackoverflow)
4. Ask the experts (if applicable)

<hr>

## Favorite Functions Work 💕

One of my favorite part of the app is the sectioned recyclers, which divides the tasks into four categories: overdue, today, upcoming tasks, and no due date (if was provided)

<hr> 

## Final App 🎉

The following are the application screenshots:

![Finall app interfaces](https://e.top4top.io/p_2133iiduy1.png)
<hr>

## Dependencies 

Material design dependency: 

```groovy
 dependencies {
    // ...
    implementation 'com.google.android.material:material:<version>'
    // ...
  }
```

Add this line to activate data binding:

```groovy
  buildFeatures {
        //...
        dataBinding true
  }
```    
