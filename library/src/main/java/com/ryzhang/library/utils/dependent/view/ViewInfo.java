package com.ryzhang.library.utils.dependent.view;

/**
 * @author ryzhang
 * @date 2017/11/6
 * @time 18:12
 * Project
 */
final class ViewInfo {
    public int value;
    public int parentId;

    ViewInfo() {
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ViewInfo viewInfo = (ViewInfo) o;
            return this.value != viewInfo.value ? false : this.parentId == viewInfo.parentId;
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.value;
        result = 31 * result + this.parentId;
        return result;
    }
}
