ISIS_Android_Viewer
===================
A generic (Naked Objects) Android app, to run against Apache Isis' Restful Objects interface.

Description

[Apache Isis](http://isis.apache.org) software is a framework for rapidly developing domain-driven apps in Java. Write your business logic in entities, domain services and repositories, and the framework dynamically generates a representation of that domain model as a webapp or a RESTful API. Use for prototyping or production. 

~~~ 

Isis works by building a metamodel from the domain object models, from which a generic user interface is generated dynamically at runtime. There are several implementations of the generic UI, one based on Wicket, one based on Servlet/JSPs, and one based on jax-rs and serving up a Restful API over http and json. This API is fully documented in the [Restful Objects spec](http://restfulobjects.org) ... there is also a (non-Apache) open source implementation on .NET. 

This GSOC suggestion is to develop a native Android app that will consume the RESTful API provided by Isis to provide a generic (naked objects) viewer for use either from a smartphone or tablet. Optionally this generic viewer could be extensible to allow mashups (as is supported by Isis' own Wicket-based viewer).

Development Video : 
* [as of 01 May 2013](http://www.youtube.com/watch?v=_POnfbxeqpE)

Setting up development environment

Isis Android client is compatible witn Android 2.2 to 4.1. So basically it can be run on any any android phone or tablet available in market.

To set up the development environment you need eclipse IDE installed with an Android sdk.

There are two projects

1- Android_Viewer
2- Android_Viewer_Test

Android_Viewer is the main project that contain the actual application and for testing purposes "Android_Viewer_Test" project is used 

To open the projects:

* file -> import -> Android -> Existing Android Code Into Workspace
* select project root
* Do this to both projects
* finish

Then you need to set up an emulator for the project.

You need to define the root of your webservice in HOST variable in com.dimuthuupeksha.viewer.android.applib.HttpHelper.java file

Finally to run the application right click on the Android_Viewer project -> run as -> Android Application.

To test the application right click on the Android_Viewer_Test project -> run as -> Android Junit Test
