Anti-Theft Social System
=======================================

Introduction
-------------

WhyMCA is an association periodically organizing, among other kind of event, hackatons.
On 18th Febryary 2012 we joined one of these competitions, named "Hack-Reality".
More information at http://www.whymca.org/evento/whymca-hack-reality-bologna-18-02-2012.

Our mission: put up a prototype system where technologies for virtual-real world interaction
were involved.

Time: 8 hours.

Result: *Best App Award* and *NetArduino Award*.

Available technologies
----------------------
ARChitect (Augmented Reality Framework by Wikitude), LEGO MINDSTORMS, Kinect, Arduino, OpenPicus,
3D Printers, Android Open ADK, .NET Micro Framework, BTicino L3504SDK, NFC for QT and so on

Our prototype
-------------

We developed a social intrusion decection system. Steps:

* A thief passes by a sensor connected to an Arduino, connected to an Android device
* The sensor notifies the Android that snaps pictures of the walking robber
* Pictures are uploaded to a remote server, where an animated GIF of the thief is created
* The GIF is published on the victim's Twitter account
* By means of Tropo APIs: 
	* phone calls are performed to the victim's neighbours to alert them
	* SMSs are sent to other people, notifying them of the theft and asking for help
* The victim receives a push notification on his/her iPhone. He can then open the application to see the 
  pictures of the robber.

Licence
-------
This code is licensed under the 2-clause BSD license ("Simplified BSD License" or "FreeBSD License") license. The license is reproduced below:

Copyright 2012 Megadevs. All rights reserved.
Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY MEGADEVS ''AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL JONATHAN WIGHT OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those of the authors and should not be interpreted as representing official policies, either expressed or implied, of Megadevs.
