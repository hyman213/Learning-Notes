# Vue组件

## 组件基础

```javascript
// 定义一个名为 button-counter 的新组件
Vue.component('button-counter', {
  data: function () {
    return {
      count: 0
    }
  },
  template: '<button v-on:click="count++">You clicked me {{ count }} times.</button>'
})
```



```vue
<div id="components-demo">
  <button-counter></button-counter>
</div>

<script>
new Vue({ el: '#components-demo' })
</script>
```

### 基本点

**每个组件必须只有一个根元素**

**data必须是一个函数，在非组件中data是一个对象，组件中data必须是一个函数**

**Prop 是你可以在组件上注册的一些自定义特性。当一个值传递给一个 prop 特性的时候，它就变成了那个组件实例的一个属性。**

```javascript
Vue.component('blog-post', {
  props: ['title'],
  template: '<h3>{{ title }}</h3>'
})
```



### 父组件监听子组件事件

子组件通过调用内建的`$emit`方法传入事件名

```html
<button v-on:click="$emit('enlarge-text')">
  Enlarge text
</button>
```

父组件通过v-on绑定该方法名，即可接收子组件的事件

```html
<blog-post
  ...
  v-on:enlarge-text="postFontSize += 0.1"
></blog-post>
```

该方式子组件也可通过事件抛出一个参数，传递给父组件。使用`$event`

子组件中

```html
<button v-on:click="$emit('enlarge-text', 0.1)">
  Enlarge text
</button>
```

父组件

```html
<blog-post
  ...
  v-on:enlarge-text="postFontSize += $event"
></blog-post>

如果是一个方法
<blog-post
  ...
  v-on:enlarge-text="onEnlargeText"
></blog-post>


<script>
...
methods: {
  onEnlargeText: function (enlargeAmount) {
    this.postFontSize += enlargeAmount
  }
}
</script>
```

### 组件上使用`v-model`

为了让它正常工作，这个组件内的 `<input>` 必须：

- 将其 `value` 特性绑定到一个名叫 `value` 的 prop 上
- 在其 `input` 事件被触发时，将新的值通过自定义的 `input` 事件抛出

```javascript
Vue.component('custom-input', {
  props: ['value'],
  template: `
    <input
      v-bind:value="value"
      v-on:input="$emit('input', $event.target.value)"
    >
  `
})
```

```html
<custom-input v-model="searchText"></custom-input>
```

### 插槽

```html
<alert-box>
  Something bad happened.
</alert-box>
```

```javascript
Vue.component('alert-box', {
  template: `
    <div class="demo-alert-box">
      <strong>Error!</strong>
      <slot></slot>
    </div>
  `
})
```

## 组件注册

### 组件名

**使用 kebab-case(短横线分隔命名) （建议）**

### 全局注册

它们在注册之后可以用在任何新创建的 Vue 根实例 (`new Vue`) 的模板中。

```vue
<div id="app">
  <component-a></component-a>
  <component-b></component-b>
  <component-c></component-c>
</div>

Vue.component('component-a', { /* ... */ })
Vue.component('component-b', { /* ... */ })
Vue.component('component-c', { /* ... */ })

new Vue({ el: '#app' })
```

### 局部注册

```vue
var ComponentA = { /* ... */ }
var ComponentB = { /* ... */ }

new Vue({
  el: '#app',
  components: {
    'component-a': ComponentA,
    'component-b': ComponentB
  }
})
```

模块系统中局部注册

```javascript
import ComponentA from './ComponentA'
import ComponentC from './ComponentC'

export default {
  components: {
    ComponentA,
    ComponentC
  },
  // ...
}
```




















