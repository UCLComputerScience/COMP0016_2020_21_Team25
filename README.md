# IBM FISE Concierge

Concierge is a voice assistant designed to make common services more accessible to elderly users in these turbulent
times.

Working as part of the **IBM FISE v2** portfolio of projects to help the elderly in times of social isolation through
voice assistance.

## Features

Concierge is designed with the end user always in mind.

- **Simple interface** - _Concierge does away with seas of menus to provide an equally powerful and intuitive method of
  navigation._

- **Privacy first** - _Concierge employs **localised speech processing** - your audio is neither recorded or stored_

- **Manage your circle** - _As an admin, you can manage every aspect of all members in your circle - from their medical
  needs to their favourite dish._

- **Check-in with loved ones** - _Users can check in with loved ones from within the app, just to let everyone know
  they're okay._

- **Personalised Experience** - _Admins can add specific services to every member of their circle - they can tailor each
  member's experience to what they need most._

- **Usage History** - _Admins can keep track of which services each user uses (not what they say) and which services are
  needed most._

- **Convenience Features** - _Concierge comes with a built-in stopwatch and custom timers and reminders to give users
  all they need in one space._

- **Tailored Responses** - _Concierge employs speech synthesis to give audio responses to the user, giving them someone
  to talk to when needed._

- **Infinitely Extensible** - _Have a service? New services can be added to the system in no time. Concierge's API
  interaction is extremely scalable._

## Deployment

There are several components to Concierge overall. These are the app, the admin site, the backend server, the Service API server and our Ask Bob plugins. Each one has its own deployment guide which can be found in their respective folders. 

Please note, to access the guide to run the Service API server, you will have to navigate to services\src\main\java\services\application where the deployment guide is given.

## Main Technologies

Concierge, powered by:

- [Vue.js](https://v3.vuejs.org) - _Admin web app_
- [Java + Android Studio](https://developer.android.com/about) - _Android App_
- [AskBob (RASA x SPACY)](https://github.com/UCL-COMP0016-2020-Team-39/AskBob) - _Natural Language Processing_

More information on the development process can be found [here](http://students.cs.ucl.ac.uk/2020/group25/index.html).

## Elderly Users

Concierge is designed with elderly users in mind who may struggle to interact with the complexities of the current
technological landscape. Available through an app, speech is used as the primary mode of communication, allowing users
to verbally issue commands and receive audio responses from their Concierge.

The Android app is designed to work on a wide array of Android devices.

More information on the Android app can be
found [here](https://github.com/UCLComputerScience/COMP0016_2020_21_Team25/wiki/Android-App).

## Friends of Elderly Users

Friends of elderly users, called admins, will be able to manage the details of the users - they can add/remove services
from their accounts to enhance their experience. To activate a user account, the admin will register their details on
the web app. Once completed, a unique three-word phrase will be generated, and this will have to be quoted on the
android app to “unlock” and activate the account for the user. Note that these codes do not expire meaning user data can
be transferred between Android devices.

More information on the web app can be
found [here](https://github.com/UCLComputerScience/COMP0016_2020_21_Team25/wiki/Admin-Web-App).

## Services

The interaction with external APIs is a standalone system - it can be pulled out and deployed into your ecosystem with
no configuration required.

The plugin system is extremely extensible, new services can easily be added with no programming knowledge required. Any
API response can be turned into a natural language sentence(s) using transformation rules you specify.

More information on the standalone external API plugin system can be
found [here](https://github.com/UCLComputerScience/COMP0016_2020_21_Team25/wiki/Services-API).

## Contributors

Concierge, brought to you by:

- [**Calin Hadarean**]() - _NLP & Backend Developer_
- [**Ernest Nkansah-Badu**]() - _Web App & Service API Plugin Developer_
- [**Mohammad Syed**]() - _App Developer & Blog Author_

Supervised by Dean Mohammedally and John McNamara.

## Acknowledgements

### Images

For the service images, shown on the admin web app, all images were taken from [unsplash](https://unsplash.com) (free
for commercial use but attributions are shown below nonetheless):

- _Food photograph
  by [Rachel Park](https://unsplash.com/@therachelstory?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText)_
- _Tram photograph
  by [zaya odeesho](https://unsplash.com/@the_zaya?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText)_
- _Sunlight photograph
  by [Syed Ali](https://unsplash.com/@syedmohdali121?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText)_
- _Charity photograph
  by [Joel Muniz](https://unsplash.com/@jmuniz?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText)_
- _Stocks
  photograph [Jamie Street](https://unsplash.com/@jamie452?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText)_
- _News photograph
  by [Markus Spiske](https://unsplash.com/@markusspiske?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText)_
- _Book photograph
  by [Alfons Morales](https://unsplash.com/@alfonsmc10?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText)_
- _Joke photograph
  by [Tim Mossholder](https://unsplash.com/@timmossholder?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText)_
- _Dictionary photograph
  by [Joshua Hoehne](https://unsplash.com/@mrthetrain?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText)_
- _Air quality photograph
  by [JuniperPhoton](https://unsplash.com/@juniperphoton?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText)_

  
