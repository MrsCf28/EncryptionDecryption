class Test {

    public static void main(String[] args) {
        new TeamLead(1);
    }

    public static class TeamLead extends Programmer {

        private static int numTeamLead;

        public TeamLead(int numTeamLead) {
            super(numTeamLead);
            this.numTeamLead = numTeamLead;
            employ();
        }

        public static void employ() {
            System.out.println(numTeamLead + " teamlead");
        }

    }

    public static class Programmer {

        private static int numProgrammer;

        public Programmer(int numProgrammer) {
            this.numProgrammer = numProgrammer;
            employ();
        }

        protected static void employ() {
            System.out.println(numProgrammer + " programmer");
        }
    }
}