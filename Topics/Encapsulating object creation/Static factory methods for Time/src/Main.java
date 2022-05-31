import java.util.Scanner;

class Time {

    int hour;
    int minute;
    int second;

    final static long maxHours = 24L;
    final static long maxMinutesOrSeconds = 60L;
    final static long secondsInHour = 3600L;

    public static Time noon() {
        Time noon = new Time();
        noon.hour = 12;
        noon.minute = 0;
        noon.second = 0;
        return noon;
    }

    public static Time midnight() {
        Time midnight = new Time();
        midnight.hour = 0;
        midnight.minute = 0;
        midnight.second = 0;
        return midnight;
    }

    public static Time ofSeconds(long seconds) {
        Time fromSeconds = new Time();
        fromSeconds.hour = (int) ((seconds / (secondsInHour)) % maxHours);
        fromSeconds.minute = (int) ((seconds / maxMinutesOrSeconds) % (maxMinutesOrSeconds));
        fromSeconds.second = (int) (seconds % maxMinutesOrSeconds);
        return fromSeconds;
    }

    public static Time of(int hour, int minute, int second) {
        Time of = new Time();
        if (hour < maxHours && hour >= 0
        && minute < maxMinutesOrSeconds && minute >= 0
        && second < maxMinutesOrSeconds && second >= 0) {
            of.hour = hour;
            of.minute = minute;
            of.second = second;
            return of;
        } else {
            return null;
        }
    }
}

/* Do not change code below */
public class Main {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        final String type = scanner.next();
        Time time = null;

        switch (type) {
            case "noon":
                time = Time.noon();
                break;
            case "midnight":
                time = Time.midnight();
                break;
            case "hms":
                int h = scanner.nextInt();
                int m = scanner.nextInt();
                int s = scanner.nextInt();
                time = Time.of(h, m, s);
                break;
            case "seconds":
                time = Time.ofSeconds(scanner.nextInt());
                break;
            default:
                time = null;
                break;
        }

        if (time == null) {
            System.out.println(time);
        } else {
            System.out.printf("%s %s %s", time.hour, time.minute, time.second);
        }
    }
}