package com.eteration.simplebanking.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BaseEntityTest {

    @Setter
    @Getter
    private static class TestEntity extends BaseEntity {
        private String name;

        public TestEntity() {}

        public TestEntity(String name) {
            this.name = name;
        }

    }

    @Test
    void testBaseEntityCreation() {
        TestEntity entity = new TestEntity("Test");
        assertNotNull(entity);
        assertEquals("Test", entity.getName());
        assertNull(entity.getId());
        assertNull(entity.getCreatedAt());
        assertNull(entity.getUpdatedAt());
        assertNull(entity.getVersion());
    }

    @Test
    void testBaseEntitySettersAndGetters() {
        TestEntity entity = new TestEntity();
        LocalDateTime now = LocalDateTime.now();
        Long id = 1L;
        Long version = 1L;
        entity.setId(id);
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        entity.setVersion(version);
        assertEquals(id, entity.getId());
        assertEquals(now, entity.getCreatedAt());
        assertEquals(now, entity.getUpdatedAt());
        assertEquals(version, entity.getVersion());
    }

    @Test
    void testBaseEntityEqualsAndHashCode() {
        TestEntity entity1 = new TestEntity("Test1");
        TestEntity entity2 = new TestEntity("Test2");
        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
        TestEntity entity3 = new TestEntity("Test1");
        assertEquals(entity1, entity3);
        assertEquals(entity1.hashCode(), entity3.hashCode());
    }

    @Test
    void testBaseEntityToString() {
        TestEntity entity = new TestEntity("Test");
        String toString = entity.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("BaseEntity"));
        assertTrue(toString.contains("id=null"));
        assertTrue(toString.contains("createdAt=null"));
        assertTrue(toString.contains("updatedAt=null"));
        assertTrue(toString.contains("version=null"));
    }

    @Test
    void testBaseEntityWithNullValues() {
        TestEntity entity = new TestEntity();
        assertNull(entity.getId());
        assertNull(entity.getCreatedAt());
        assertNull(entity.getUpdatedAt());
        assertNull(entity.getVersion());
    }
} 