ISIS_Android_Viewer
===================
A generic (Naked Objects) Android app, to run against Apache Isis' Restful Objects interface.

Description

[Apache Isis](http://isis.apache.org) software is a framework for rapidly developing domain-driven apps in Java. Write your business logic in entities, domain services and repositories, and the framework dynamically generates a representation of that domain model as a webapp or a RESTful API. Use for prototyping or production. 

Isis works by building a metamodel from the domain object models, from which a generic user interface is generated dynamically at runtime. There are several implementations of the generic UI, one based on Wicket, one based on Servlet/JSPs, and one based on jax-rs and serving up a Restful API over http and json. This API is fully documented in the [Restful Objects spec](http://restfulobjects.org) ... there is also a (non-Apache) open source implementation on .NET. 

This GSOC suggestion is to develop a native Android app that will consume the RESTful API provided by Isis to provide a generic (naked objects) viewer for use either from a smartphone or tablet. Optionally this generic viewer could be extensible to allow mashups (as is supported by Isis' own Wicket-based viewer).

Setting up development environment

Isis Android client is compatible with Android 2.2 to 4.2. So basically it can be run on any any android phone or tablet available in market.

To set up the development environment you need eclipse IDE installed with an Android sdk.

There are two projects

* Android_Viewer
* Android_Viewer_Test

And one library project

* JakeWharton-ActionBarSherlock

Android_Viewer is the main project that contain the actual application and for testing purposes "Android_Viewer_Test" project is used 

To open these projects

1 - Import the supporting library project

* file -> import -> Android -> Existing Android Code Into Workspace
* project folder->JakeWharton-ActionBarSherlock->library
* Go to properties and in the Android tab make sure the check box with the title "Library" is ticked

2- Import other two projects

* file -> import -> Android -> Existing Android Code Into Workspace
* select project root
* Do this to both projects
* finish

For more information follow this [video](http://www.youtube.com/watch?v=szkZLmmW_r0)

Then you need to set up an emulator for the project.

You need to define the root of your webservice in HOST variable in com.dimuthuupeksha.viewer.android.applib.HttpHelper.java file

Finally to run the application, right click on the Android_Viewer project -> run as -> Android Application.

Here are some demo videos of the application

1. [Demo Video 1](http://www.youtube.com/watch?v=5MKg3W7pXZA)
2. [Demo Video 2](http://www.youtube.com/watch?v=VBc-Zg-nH5Y)
3. [Demo Video 3](http://www.youtube.com/watch?v=AtMPW0B-ZRE)

To run the tests of the application, right click on the Android_Viewer_Test project -> run as -> Android Junit Test
