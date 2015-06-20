package pdfstamper

/************************************************************************
 * This is free software - without ANY guarantee!
 *
 *
 * Copyright 2013, Dr. Gernot Starke, arc42.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *********************************************************************** */

class HorizontalPositions {
    static int INSIDE = 0
    static int CENTER = 1
    static int OUTSIDE = 2


    static List positions =
            [ "inside",
              "center",
              "outside"];

    static List explanation =
            [ "inside: page number left on even pages, right on odd pages",
              "centered: horizontally centered for all pages",
              "outside: even pages: right, odd pages: left"]
}
