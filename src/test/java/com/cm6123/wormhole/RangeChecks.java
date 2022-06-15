package com.cm6123.wormhole;

import com.cm6123.wormhole.utils.Range;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RangeChecks {
    @Test
    public void shouldContain2IfRange1To3() {
        Range r = new Range(1, 3);
        assertEquals(r.contains(2), true);
    }

    @Test
    public void shouldNotContain4IfRange1To3() {
        Range r = new Range(1, 3);
        assertEquals(r.contains(4), false);
    }
}
