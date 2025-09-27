package com.mario.faceengine.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlowTest {

    @Test
    void testFlow_AllEnumValues_ShouldHaveCorrectFlowStrings() {
        assertEquals("REGISTER", Flow.REGISTER.getFlow());
        assertEquals("MATCHING", Flow.MATCHING.getFlow());
        assertEquals("RECOGNIZE", Flow.RECOGNIZE.getFlow());
        assertEquals("DELETE", Flow.DELETE.getFlow());
        assertEquals("NONE", Flow.NONE.getFlow());
    }

    @Test
    void testFlow_EnumValues_ShouldNotBeNull() {
        assertNotNull(Flow.REGISTER);
        assertNotNull(Flow.MATCHING);
        assertNotNull(Flow.RECOGNIZE);
        assertNotNull(Flow.DELETE);
        assertNotNull(Flow.NONE);
    }

    @Test
    void testFlow_GetFlow_ShouldReturnNonNullValues() {
        for (Flow flow : Flow.values()) {
            assertNotNull(flow.getFlow());
            assertFalse(flow.getFlow().isEmpty());
        }
    }

    @Test
    void testFlow_EnumCount_ShouldHaveFiveValues() {
        Flow[] flows = Flow.values();
        assertEquals(5, flows.length);
    }
}
