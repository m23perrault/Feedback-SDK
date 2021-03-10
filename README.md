Feedback &amp; Rater SDK Android

[![](https://jitpack.io/v/m23perrault/Feedback-SDK.svg)](https://jitpack.io/#m23perrault/Feedback-SDK)


<p><a title="#1 Conservative News Aggregator" href="https://www.conservativenewsdaily.net/">#1 Conservative News Aggregator</a></p>


## Screenshots

<img src="/screenshot/ss_1.png" width="280" height="470"> <img src="/screenshot/ss_2.png" width="280" height="470"> <img src="/screenshot/ss_3.png" width="280" height="470">
<img src="/screenshot/ss_4.png" width="280" height="470"> <img src="/screenshot/ss_5.png" width="280" height="470"> 




Feedback SDK is an in-app feedback  tool for mobile apps. 

## Installation

### Gradle

Downloads

Tag : 1.1

```groovy
To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
Step 2. Add the dependency

dependencies {
	        implementation 'com.github.m23perrault:Feedback-SDK:Tag'
	}
  
```

### or Maven

```groovy

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>


Step 2. Add the dependency

	<dependency>
	    <groupId>com.github.m23perrault</groupId>
	    <artifactId>Feedback-SDK</artifactId>
	    <version>Tag</version>
	</dependency>

```

## Usage

```groovy
Apptenium Link : http://www.apptenium.com
First create your account on apptenium.com.
After that register your app on the Web SDK and getting the APP_ID & SECRET_KEY from the SDK and Store in your App:-

Then goto you app code:

	/*
        * Firslty Define the SDK In your Application Class
	* @param Context
        * @param SDK_APP_ID
        * @param SDK_APP_SECRET_KEY
        * The SDKInstance is Mendatory for both
        * App Rater & Feedback
         /

        new SDKInstance(getApplicationContext(), "SDK_APP_ID", "SDK_APP_SECRET_KEY");


<--FEEDBACK-->

 In your `Activity/Fragment` class from where you link the feedback option, add these lines.

  ##Activity
 //Pass SDK AppID & SDK SecretKey in SDKInstance.
 new SDKInstance("SDK_APP_ID", "SDK_APP_SECRET_KEY");
 startActivity(new Intent(YourActivityName.this, FeedbackActivity.class));
 
  ##Fragment
  //Pass SDK AppID & SDK SecretKey in SDKInstance.
  new SDKInstance("SDK_APP_ID", "SDK_APP_SECRET_KEY");
  startActivity(new Intent(getActivity(), FeedbackActivity.class));
 
<--RATINGS BOOSTER-->

 Decide which Activity/Fragment you want to appear the rating prompt in (usually your MainActivity.java / MainFragment.java).
   
   
   For Default/Normal funcationality where all default texts , days prompts & app launch prompts are mention below   :
    DEFAULT_TITLE = "Rate Me";
    DEFAULT_RATE_TEXT = "Hi! If you like this App, can you please take a few minutes to rate it? It help so much when you do , thanks!";
    DEFAULT_YES_RATE = "Yes";
    DEFAULT_MAY_BE_LATER = "Maybe Later";
    DEFAULT_NO_THANKS = "No, Thanks";
    DEFAULT_DAYS_BEFORE_PROMPT = 3;
    DEFAULT_LAUNCH_BEFORE_PROMPT = 7;
	
	##Activity
   At the end of your Activity's onCreate(...) or onResume(...), add the following:

   new SDKInstance("SDK_APP_ID", "SDK_APP_SECRET_KEY");
   AppRater mAppRater = new AppRater(mActivity,"YOUR_APP_PACKAGE_NAME");
   mAppRater.show();


   ##Fragment
   If you want to call the RatingsBooster from a Fragment (e.g. at the end of onCreateView()), use the following line instead:

   new SDKInstance("SDK_APP_ID", "SDK_APP_SECRET_KEY");
   AppRater mAppRater = new AppRater(getActivity(),"YOUR_APP_PACKAGE_NAME");
   mAppRater.show();
   
   ##FOR CUSTOMIZATION FUNCTIONALITY:
   Where everything mention to your according link popup texts , days prompts and launch prompts etc....
   Then use the custom methods of Rating Booster:
   
    
   new SDKInstance("SDK_APP_ID", "SDK_APP_SECRET_KEY");
   AppRater mAppRater = new AppRater(mActivity,"YOUR_APP_PACKAGE_NAME");


        mAppRater.setTitle("Rate Our App")
                .setRateText("Hi! If you like this app, Then please rate & review on google play store")
                .setPositiveButtonText("Yes! Rate Now")
                .setNegativeButtonText("No, Thanks")
                .setMayBeLaterButtonText("Next Time")
                .setBeforeDaysPrompt(3)
                .setBeforeAppLaunchPrompt(5);

        mAppRater.setSelectRateText("Are you happy with app, Do you want to send feedback or rating for app?")
                 .setSelectPositiveButtonText("Submit")
                 .setSelectNegativeButtonText("Cancel")
                 .show();


## Notes

Some permissions are automatically added to your AndroidManifest.xml file. Some of them are required to be able to fetch some information like the network connection.
```xml
<uses-permission android:name=“android.permission.ACCESS_NETWORK_STATE” />
<uses-permission android:name=“android.permission.INTERNET” />
```


## More


