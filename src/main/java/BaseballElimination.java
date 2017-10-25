import edu.princeton.cs.algs4.*;

import java.util.*;

public final class BaseballElimination {

    private int[] wins;
    private int[] losses;
    private int[] remaining;
    private Map<String, Integer> teamToId;
    private String[] teams;
    private int[][] g;

    private int numberOfTeams;

    private boolean[] isEliminated;
    private List<Set<String>> r;

    public BaseballElimination(String filename) {
        In in = new In(filename);

        // read number of teams
        this.numberOfTeams = in.readInt();

        // create wins/losses/remaining
        this.wins = new int[this.numberOfTeams];
        this.losses = new int[this.numberOfTeams];
        this.remaining = new int[this.numberOfTeams];

        teamToId = new HashMap<String, Integer>();
        teams = new String[numberOfTeams];

        this.g = new int[numberOfTeams][numberOfTeams];

        for (int k = 0; k < numberOfTeams; k++) {
            String teamName = in.readString();
            teamToId.put(teamName, k);

            wins[k] = in.readInt();
            losses[k] = in.readInt();
            remaining[k] = in.readInt();

            for (int n = 0; n < numberOfTeams; n ++) {
                g[k][n] = in.readInt();
            }
            teams[k] = teamName;
        }

        isEliminated = new boolean[numberOfTeams];
        r = new ArrayList<Set<String>>();
        for (int i = 0; i < numberOfTeams; i++) {
            r.add(new TreeSet<>());
        }
        for (String team : teamToId.keySet()) {
            compute(team);
        }
    }

    public int numberOfTeams() {
        return this.numberOfTeams;
    }

    public Iterable<String> teams() {
        return teamToId.keySet();
    }

    public int wins(String team) {
        checkTeam(team);
        return wins[teamToId.get(team)];
    }

    public int losses(String team) {
        checkTeam(team);
        return losses[teamToId.get(team)];
    }

    public int remaining(String team) {
        checkTeam(team);
        return remaining[teamToId.get(team)];
    }

    public int agaist(String team1, String team2) {
        checkTeam(team1);
        checkTeam(team2);
        return g[teamToId.get(team1)][teamToId.get(team2)];
    }

    void compute(String team) {
        checkTeam(team);

        int x = teamToId.get(team);

        // trivial
        for (int i = 0; i < numberOfTeams; i++) {
            if (x == i) {
                continue;
            }

            if (wins[x] + remaining[x] < wins[i]) {
                isEliminated[x] = true;
                r.get(x).add(teams[i]);
                return;
            }
        }

        // nontrivial
        int numberOfMatches = numberOfTeams * (numberOfTeams - 1) / 2;
        FlowNetwork flowNetwork = new FlowNetwork(1 + numberOfMatches + numberOfTeams + 1);

        // coloca s e t no final do array do flowNetwork
        int s = numberOfMatches + numberOfTeams;
        int t = s + 1;

        int nodeId = 0;
        for (int i = 0; i < numberOfTeams; i++) {
            for (int j = i + 1; j < numberOfTeams; j++) {
                // liga s a cada match
                flowNetwork.addEdge(new FlowEdge(s, nodeId, g[i][j]));

                // liga o no aos outros elementos
                flowNetwork.addEdge(new FlowEdge(nodeId, numberOfMatches + i, Integer.MAX_VALUE));
                flowNetwork.addEdge(new FlowEdge(nodeId, numberOfMatches + j, Integer.MAX_VALUE));

                nodeId++;
            }

            flowNetwork.addEdge(new FlowEdge(numberOfMatches + i, t, wins[x] + remaining[x] - wins[i]));
        }

        FordFulkerson FF = new FordFulkerson(flowNetwork, s, t);
        for (int i = numberOfMatches; i < numberOfMatches + numberOfTeams; i++) {
            if (FF.inCut(i)) {
                r.get(x).add(teams[i -numberOfMatches]);
            }
        }

        for (FlowEdge e : flowNetwork.adj(s)) {
            if (e.flow() != e.capacity()) {
                isEliminated[x] = true;
            }
        }
    }

    boolean isEliminated(String team) {
        checkTeam(team);
        return isEliminated[teamToId.get(team)];
    }

    public Iterable<String> certificateOfElimination(String team) {
        checkTeam(team);
        return r.get(teamToId.get(team));
    }

    private void checkTeam(String team) {
        if (team == null || !teamToId.containsKey(team)) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }

}
