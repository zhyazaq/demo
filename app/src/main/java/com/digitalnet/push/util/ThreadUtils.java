/*
 * Copyright 2016 北京博瑞彤芸文化传播股份有限公司
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.digitalnet.push.util;


import com.digitalnet.push.base.BaseApplication;


public class ThreadUtils {
    /**
     *
     * @return 是否是主线程
     */
    public static boolean isMainThread() {
        return "main".equals(Thread.currentThread().getName());
    }

    public static void runOnUiThread(Runnable runnable) {
        runOnUiThread(runnable, 0);
    }

    public static void runOnUiThread(Runnable r, long delayMillis) {
        BaseApplication.getHandle().postDelayed(r, delayMillis);
    }
}
