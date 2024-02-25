package cz.czechitas.ukol4;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Spouštěcí třída celého programu
 */
public class HlavniProgram {

    public static void main(String... args) {
        Sportka prvniTah = new Sportka();
        Sportka druhyTah = new Sportka();

        System.out.println("Probíhá losování prvního tahu Sportky…");
        prvniTah.zamichej();
        overVysledky(prvniTah);
        vypisVysledky(prvniTah);

        System.out.println();

        System.out.println("Probíhá losování druhého tahu Sportky…");
        druhyTah.zamichej();
        overVysledky(druhyTah);
        vypisVysledky(druhyTah);

        System.out.println();
        System.out.println("Výhercům gratulujeme");

    }

    private static void vypisVysledky(Sportka sportka) {
        System.out.printf("Vylosovaná čísla: %s.", sportka.dejVylosovanaCisla()
                        .stream()
                        .map(cislo -> Integer.toString(cislo))
                        .collect(Collectors.joining(", ")))
                .println();
        System.out.printf("Dodatkové číslo: %d.", sportka.dejDodatkoveCislo())
                .println();
    }

    private static void overVysledky(Sportka sportka) {
        List<Integer> vylosovanaCisla = Objects.requireNonNull(sportka.dejVylosovanaCisla(), "Chybí vylosovaná čísla.");
        Integer dodatkoveCislo = Objects.requireNonNull(sportka.dejDodatkoveCislo(), "Chybí dodatkové číslo.");

        if (vylosovanaCisla.size() != 6) {
            throw new IllegalArgumentException(String.format("Vylosovaných čísel musí být 6, ve skutečnosti jich je %d.", vylosovanaCisla.size()));
        }

        vylosovanaCisla.forEach(HlavniProgram::overCislo);
        overCislo(dodatkoveCislo);

        Set<Integer> tazenaCisla = new HashSet<>();
        tazenaCisla.add(dodatkoveCislo);
        for (Integer tazeneCislo : vylosovanaCisla) {
            if (tazenaCisla.contains(tazeneCislo)) {
                throw new IllegalArgumentException(String.format("Číslo %d bylo vyteženo z osudí dvakrát.", tazeneCislo));
            }
            tazenaCisla.add(tazeneCislo);
        }

        System.out.println("Státní notář ověřil tah a vylosované čísla jsou platná.");
    }

    private static void overCislo(Integer cislo) {
        if (cislo < 1 || cislo > 49) {
            throw new IllegalArgumentException(String.format("Vylosované číslo %d je mimo očekávaný rozsah 1–49.", cislo));
        }
    }

}
