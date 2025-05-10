package com.testing.app.common.entity.watch_set;

import com.testing.app.common.entity.AbstractEntity;
import org.springframework.beans.BeanUtils;

import java.util.*;
import java.util.stream.Collectors;

public class TrackedSet<T extends AbstractEntity> extends HashSet<T> {

    private final Set<ObjectTrackerKey> adds = new HashSet<>();
    private final Map<ObjectTrackerKey, T> removedValues = new HashMap<>();
    private final Set<ObjectTrackerKey> updates = new HashSet<>(); // Thêm set để theo dõi cập nhật

    @Override
    public boolean add(T t) {
        boolean changed = super.add(t);
        if (changed) {
            ObjectTrackerKey key = keyOf(t);
            adds.add(key);
            removedValues.remove(key);
            updates.remove(key); // Xóa khỏi danh sách cập nhật khi nó được thêm mới
        }
        return changed;
    }

    @Override
    public boolean remove(Object o) {
        boolean changed = super.remove(o);
        if (changed) {
            ObjectTrackerKey key = keyOf((T) o);
            removedValues.put(key, (T) o);
            adds.remove(key);
            updates.remove(key); // Xóa khỏi danh sách cập nhật khi nó bị xóa
        }
        return changed;
    }

    public boolean update(T t) {
        Object id = t.getId();
        Optional<T> oldItem = this.stream().filter(t1 -> Objects.equals(t1.getId(), id)).findFirst();

        oldItem.ifPresent(
                existing -> {
                    BeanUtils.copyProperties(t, existing);
                    updates.add(keyOf(existing));
                });
        return oldItem.isPresent();
    }

    @Override
    public void clear() {
        this.forEach(
                t -> {
                    ObjectTrackerKey key = keyOf(t);
                    removedValues.put(key, t);
                });
        adds.clear();
        updates.clear();
        super.clear();
    }

    // Phương thức này sẽ được gọi khi bạn muốn đánh dấu một phần tử đã được cập nhật
    public void markAsUpdated(T t) {
        ObjectTrackerKey key = keyOf(t);
        if (!adds.contains(key) && !removedValues.containsKey(key)) {
            updates.add(key); // Chỉ đánh dấu nếu là phần tử đã tồn tại
        }
    }

    // Trả về các giá trị đã thêm
    public Set<T> getAddedValues() {
        return this.stream().filter(t -> adds.contains(keyOf(t))).collect(Collectors.toSet());
    }

    // Trả về các giá trị đã xóa
    public Set<T> getRemovedValues() {
        return Set.copyOf(removedValues.values());
    }

    // Trả về các giá trị đã được cập nhật
    public Set<T> getUpdatedValues() {
        return this.stream().filter(t -> updates.contains(keyOf(t))).collect(Collectors.toSet());
    }

    // Kiểm tra nếu chỉ có đối tượng đã thêm
    public boolean isOnlyAdded() {
        return !adds.isEmpty() && removedValues.isEmpty() && updates.isEmpty();
    }

    // Kiểm tra nếu chỉ có đối tượng đã xóa
    public boolean isOnlyRemoved() {
        return adds.isEmpty() && !removedValues.isEmpty() && updates.isEmpty();
    }

    // Kiểm tra nếu chỉ có đối tượng đã cập nhật
    public boolean isOnlyUpdated() {
        return adds.isEmpty() && removedValues.isEmpty() && !updates.isEmpty();
    }

    public boolean hasChanges() {
        return !adds.isEmpty() || !removedValues.isEmpty() || !updates.isEmpty();
    }

    public ChangeSet<T> getChangeSet() {
        return new ChangeSet<>(getAddedValues(), getRemovedValues(), getUpdatedValues());
    }

    private ObjectTrackerKey keyOf(T entity) {
        return new ObjectTrackerKey(entity.getId());
    }

    // Inner class gọn gàng cho tổng hợp kết quả thay đổi
    public record ChangeSet<T>(Set<T> added, Set<T> removed, Set<T> updated) {
    }
}
