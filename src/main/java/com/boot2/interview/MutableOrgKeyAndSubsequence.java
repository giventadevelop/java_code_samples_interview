package com.boot2.interview;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Interview question: mutable {@code Org} used as a {@link HashMap} key after {@code setName}.
 *
 * <p>Subsequence check lives in {@link ArraySubsequenceChecker}.</p>
 *
 * <p>Run: {@code java -cp target/classes com.boot2.interview.MutableOrgKeyAndSubsequence}</p>
 */
public class MutableOrgKeyAndSubsequence {

    /**
     * Org key whose equals/hashCode use <b>both</b> id and name (as in the interview sketch).
     *
     * <h3>Depends on whether equals/hashCode are overridden</h3>
     * <table border="1" summary="a1 after setName">
     *   <tr><th>equals / hashCode</th><th>Value of {@code a1 = m.get(o1)}</th></tr>
     *   <tr>
     *     <td><b>Not overridden</b> (Object defaults: identity)</td>
     *     <td><b>{@code "Hinjewadi"}</b> — your answer is correct.
     *         {@code setName} does not change identity hashCode or {@code ==}-based equals,
     *         so the same object still finds the same map entry.</td>
     *   </tr>
     *   <tr>
     *     <td><b>Overridden on id + name</b> (interview sketch comments)</td>
     *     <td><b>{@code null}</b> — hash changes with name; get looks in the wrong bucket.</td>
     *   </tr>
     * </table>
     *
     * <h3>Why {@code a1} is {@code null} when id+name are in hashCode/equals</h3>
     * <ul>
     *   <li>{@code put(o1, "Hinjewadi")} stored the entry under hash(id=1, name="Infosys").</li>
     *   <li>{@code o1.setName("WIPRO")} mutates the key already inside the map → its hashCode changes.</li>
     *   <li>{@code get(o1)} looks in the bucket for hash(id=1, name="WIPRO") → different bucket → miss → {@code null}.</li>
     * </ul>
     *
     * <h3>How we fix it (suggestions)</h3>
     * <ul>
     *   <li><b>Best:</b> make map keys immutable (no setters; final fields). Never mutate a key after put.</li>
     *   <li><b>If identity is only the id:</b> base equals/hashCode on {@code id} only (name is display data).</li>
     *   <li><b>If you must rename:</b> {@code remove} old key → change name → {@code put} again (or use a new Org instance).</li>
     *   <li>Prefer {@code Map<Integer, String>} (id → address) when the real key is the org id.</li>
     * </ul>
     */
    public static class Org {
        private final int id;
        private String name;

        public Org(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        /** Mutating name after put breaks HashMap lookup when hashCode includes name. */
        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Org)) {
                return false;
            }
            Org other = (Org) o;
            return id == other.id && Objects.equals(name, other.name);
        }

        @Override
        public String toString() {
            return "Org{id=" + id + ", name='" + name + "'}";
        }
    }

    /**
     * Fixed Org: identity = id only. Renaming no longer moves the HashMap bucket.
     * Still safer to avoid mutating keys; this shows the id-only equals/hashCode fix.
     */
    public static class OrgById {
        private final int id;
        private String name;

        public OrgById(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public int hashCode() {
            return Integer.hashCode(id);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof OrgById)) {
                return false;
            }
            return id == ((OrgById) o).id;
        }
    }

    /**
     * Demonstrates broken vs fixed HashMap key behavior.
     *
     * <p>If the sketch <b>did</b> override equals/hashCode on id+name → {@code a1 == null}.
     * If those methods were <b>not</b> overridden → {@code a1 == "Hinjewadi"} (identity lookup).</p>
     */
    public static void demoMutableOrgKey() {
        System.out.println("=== Q1: Mutable Org as HashMap key ===");

        Map<Org, String> m = new HashMap<>();
        Org o1 = new Org(1, "Infosys");
        m.put(o1, "Hinjewadi");

        o1.setName("WIPRO");
        String a1 = m.get(o1);

        // Interview claim "Answer is Hinjewadi" is incorrect for id+name hashCode/equals.
        System.out.println("After setName(\"WIPRO\"), m.get(o1) = " + a1); // null
        System.out.println("Expected: null (key hash changed; entry stranded under old hash)");

        // Fix option A: id-only equals/hashCode
        Map<OrgById, String> fixed = new HashMap<>();
        OrgById o2 = new OrgById(1, "Infosys");
        fixed.put(o2, "Hinjewadi");
        o2.setName("WIPRO");
        System.out.println("Fixed OrgById lookup = " + fixed.get(o2)); // Hinjewadi

        // Fix option B: never mutate — put a new key instance and remove old if needed
        Map<Org, String> immutableStyle = new HashMap<>();
        Org before = new Org(1, "Infosys");
        immutableStyle.put(before, "Hinjewadi");
        Org after = new Org(1, "WIPRO");
        String address = immutableStyle.remove(before);
        immutableStyle.put(after, address);
        System.out.println("Remove+re-put style lookup = " + immutableStyle.get(after)); // Hinjewadi
    }

    public static void main(String[] args) {
        demoMutableOrgKey();
    }
}
