

# **CUISINE CRAVINGS**

## Table of Contents

1. [App Overview](#App-Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
1. [Build Notes](#Build-Notes)

## App Overview

### Description 

**
An app that focuses on giving people new foods based on wherever they are in the world! Allowing for new experiences while also respecting the culture, allowing for community, fun, and memories to be had!**

### App Evaluation

<!-- Evaluation of your app across the following attributes -->

- **Category:** Food
- **Mobile:** Quick inspiration for when traveling or when cooking.
- **Story:** When making plans when hanging out with friends and you can't decide where to eat. This makes it easier to decide and enjoy.
- **Market:** Food lovers and travelers
- **Habit:** Frequently for those who need to eat new foods
- **Scope:** Reasonable? API documentation is easy to understand. Simple query parameters for ingredients and region.

## Product Spec

### 1. User Features (Required and Optional)

Required Features:

- Country selection
- Get image and display its ingredients based on selected region using an API
- In recyclerview, show the recipe title, image, ingredients and two button which link to a Youtube tutorial and recipe.
- Random dish generator button

Stretch Features:

- Favorite a dish

### 2. Chosen API(s)

Main API source: https://www.themealdb.com/api.php
- www.themealdb.com/api/json/v1/1/list.php?a=list
    - List of regions to choose your meals from
- www.themealdb.com/api/json/v1/1/lookup.php?i=52772
  - Get a meals full details by id
      - Title
      - Ingredients
      - Instructions
      - Youtube Tutorial
- www.themealdb.com/api/json/v1/1/random.php
    - Look up a single random meal

### 3. User Interaction

Required Feature:

- **User is able to view a variety of dishes, allowing for no lack of variety.**
    - User selects their particular country, which is used as an indicator/tag.
  -  App will filter dishes based on their country
  - ...
- **User is provided with multitude of features**
  - => App will contain a random mode, a single, random dish regardless of the tag is brought onto the user's screen.

- Each dish is interactable, when faced with a dish an individual is interested in, the user can click it:
    - When the dish is clicked, it takes the user to a seperate screen that showcases the dish alongside it's details( Name, ingredients, place of origin, etc )

## Wireframes


<img src="https://i.imgur.com/0QGtRo5.png" height=600>

### [BONUS] Digital Wireframes & Mockups

<img src="https://i.imgur.com/kVILgoY.png" height=600>



### [BONUS] Interactive Prototype

## Build Notes

Here's a place for any other notes on the app, it's creation 
process, or what you learned this unit!  

### Features:
- [x] Random food button
- [x] Country and category selection of food
- [x] Search button
- [x] Recycler view of food
- [x] More info button with 
- [x] Youtube tutorial button 
- [x] Recipe button 
- [x] Back button
- [ ] Favorite dish

### 2 + Videos/GIFs of Build Process

![Alt Text](https://i.imgur.com/glerN9V.gif)

#### Implementation of: 
- Back button 
- Random dish button 
- More info button  
- Link to recipe

[Watch video demo](https://imgur.com/gallery/joshua-nwabuzor-cuisine-cravings-surprise-button-android-capstone-9s2dpBG)




#### Implemention of:
- Search feature
- Country/Category selection

[Watch video demo](https://imgur.com/gallery/and101-final-program-gif-EZMCgZP#glerN9V)


## License

Copyright **2025** **Jeannette Ruiz, Angel Rivera, Keerthi Sreeram, & Joshua Nwabuzor**

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
