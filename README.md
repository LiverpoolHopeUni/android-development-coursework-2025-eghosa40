[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/DS3PLs8n)

# Mood Logger

Offline Android app to track daily moods and personal notes.  
Built for COMH019 Android Development Coursework.

---

## 📱 Overview

Mood Logger helps you log your mood and add optional notes each day.  
The app stores all data locally and works fully offline.

Designed using:
- Single Activity architecture
- Navigation Components
- Fragments
- Floating Action Button (FAB)
- Material Design 3

---

## 🎯 Key Features

- ✅ Log your mood with quick mood selection (radio buttons)
- ✅ Add optional notes for extra details
- ✅ Save mood entries locally using Room Database
- ✅ View mood history in chronological order (planned or implemented)
- ✅ Smooth dark mode support with `Theme.Material3.DayNight.NoActionBar`
- ✅ Edge-to-edge display on devices with notches
- ✅ Save button remains disabled until valid input is detected
- ✅ Handles screen rotation (form state persists after rotation)
- ✅ Custom app launcher icon for polished look
- ✅ Clean modern UI using Material Components

---

## 🗺️ App Flow

**Dashboard Fragment**
- Entry point screen
- Tap Floating Action Button to add a mood

**Add Mood Fragment**
- Select mood and/or type an optional note
- Save mood triggers Material Alert Dialog confirmation
- Data persists locally in Room Database

---

## 🛠️ Technologies

- Android Studio Hedgehog
- Java
- Android SDK 34
- Material Design 3
- Room Persistence Library
- Navigation Components
- Single Activity + Fragment architecture

---

## 💾 Notes

This project was created as coursework for the COMH019 Android Development module.  
No external APIs or Firebase were used. All data is stored locally for privacy and offline capability.
