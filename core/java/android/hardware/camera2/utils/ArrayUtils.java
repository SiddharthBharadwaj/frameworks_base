/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.hardware.camera2.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Various assortment of array utilities.
 */
public class ArrayUtils {

    private static final String TAG = "ArrayUtils";
    private static final boolean VERBOSE = Log.isLoggable(TAG, Log.VERBOSE);

    /** Return the index of {@code needle} in the {@code array}, or else {@code -1} */
    public static <T> int getArrayIndex(T[] array, T needle) {
        if (needle == null) {
            return -1;
        }

        int index = 0;
        for (T elem : array) {
            if (Objects.equals(elem, needle)) {
                return index;
            }
            index++;
        }

        return -1;
    }

    /**
     * Create an {@code int[]} from the {@code List<>} by using {@code convertFrom} and
     * {@code convertTo} as a one-to-one map (via the index).
     *
     * <p>Strings not appearing in {@code convertFrom} are ignored (with a logged warning);
     * strings appearing in {@code convertFrom} but not {@code convertTo} are silently
     * dropped.</p>
     *
     * @param list Source list of strings
     * @param convertFrom Conversion list of strings
     * @param convertTo Conversion list of ints
     * @return An array of ints where the values correspond to the ones in {@code convertTo}
     *         or {@code null} if {@code list} was {@code null}
     */
    public static int[] convertStringListToIntArray(
            List<String> list, String[] convertFrom, int[] convertTo) {
        if (list == null) {
            return null;
        }

        List<Integer> convertedList = new ArrayList<>(list.size());

        for (String str : list) {
            int strIndex = getArrayIndex(convertFrom, str);

            // Guard against unexpected values
            if (strIndex < 0) {
                Log.w(TAG, "Ignoring invalid value " + str);
                continue;
            }

            // Ignore values we can't map into (intentional)
            if (strIndex < convertTo.length) {
                convertedList.add(convertTo[strIndex]);
            }
        }

        int[] returnArray = new int[convertedList.size()];
        for (int i = 0; i < returnArray.length; ++i) {
            returnArray[i] = convertedList.get(i);
        }

        return returnArray;
    }

    private ArrayUtils() {
        throw new AssertionError();
    }
}
