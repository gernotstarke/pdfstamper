import org.gradle.launcher.GradleMain;

public class GradleRunner {
        public static void main(String[] args) throws Exception {
            System.out.println("Hello gradle, are you there?");
            try {
                GradleMain.main(new String[]{"helloWorld"});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
