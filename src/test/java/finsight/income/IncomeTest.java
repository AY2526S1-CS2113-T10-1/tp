package finsight.income;

import finsight.income.exceptions.AddIncomeCommandWrongFormatException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class IncomeTest {

    @Test
    void income_amountEarnedConsistOfAlphabet_exceptionThrown(){
        assertThrows(AddIncomeCommandWrongFormatException.class, ()-> new Income("Work","onehundred"));
    }
}
