/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *  Copyright (c) 2024 Grégory Van den Borre
 *  More infos available: https://engine.yildiz-games.be
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 *  the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright
 *  notice and this permission notice shall be included in all copies or substantial portions of the  Software.
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 *  OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package be.yildizgames.common.csv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * @author Grégory Van den Borre
 */
public class T {

    public static void main(String[] args) {
        System.out.println((System.currentTimeMillis() / 1000) + 40);
        System.out.println((System.currentTimeMillis() / 1000) + 60);
       /* try {
            var result = new ArrayList<>();
            var lines = Files.readAllLines(Path.of("D:/ll.csv")).stream().map(T::map).toList();

            for(int i = 1; i < lines.size(); i+=5) {
                result.add(new Day(toTs(lines.get(i)), toTs(lines.get(i+1)), toTs(lines.get(i+2)), toTs(lines.get(i+3)), toTs(lines.get(i+4))));
            }
            System.out.println(String.join(",",result.stream().map(Object::toString).toList()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }

    private static long toTs(Time time) {
        String dateStr = time.date;
        String timeStr = time.time;

        var formatter = DateTimeFormatter.ofPattern("d/M/yyyy H:mm");

        String dateTimeStr = dateStr + " " + timeStr;
        var dateTime = LocalDateTime.parse(dateTimeStr, formatter);
        return dateTime.toEpochSecond(ZoneOffset.UTC);
    }

    public static Time map(String line) {
        var values = line.split(",");
        return new Time(values[1], values[2]);
    }

    public record Day (long fajr, long dhor, long asr, long maghreb, long isha) {

        @Override
        public String toString() {
            return fajr + "," + dhor + "," + asr + "," + maghreb + "," + isha;
        }
    }

    public record Time (String date, String time) {


    }
}
