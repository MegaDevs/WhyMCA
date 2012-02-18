// Copyright 2011 StackMob, Inc
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

#if DEBUG
    #define SMLog(format, ...) {NSLog(format, ##__VA_ARGS__);}
    #define StackMobDebug(format, ...) {NSLog([[NSString stringWithFormat:@"[%s, %@, %d] ", __PRETTY_FUNCTION__, [[NSString stringWithUTF8String:__FILE__] lastPathComponent], __LINE__] stringByAppendingFormat:format, ##__VA_ARGS__]);}
#else
    #define SMLog(format, ...) {NSLog(format, ##__VA_ARGS__);}
    #define StackMobDebug(format, ...) {NSLog([[NSString stringWithFormat:@"[%s, %@, %d] ", __PRETTY_FUNCTION__, [[NSString stringWithUTF8String:__FILE__] lastPathComponent], __LINE__] stringByAppendingFormat:format, ##__VA_ARGS__]);}
#endif


#define STACKMOB_PUBLIC_KEY         @"31fbb356-4d5b-48b6-9893-980a3efb275e"
#define STACKMOB_PRIVATE_KEY        @"daf26000-dfb3-4e2d-b8e0-1617a0774a2b"
#define STACKMOB_APP_NAME           @"atss"
#define STACKMOB_APP_SUBDOMAIN      @"c3c84909770641468142d82cc8cf385a"
#define STACKMOB_APP_DOMAIN         @"stackmob.com"
#define STACKMOB_USER_OBJECT_NAME   @"user"
#define STACKMOB_API_VERSION        0
