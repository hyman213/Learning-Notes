# Vue基础

> :artificial_satellite:笔记基于Vue 2.x版本

Vue (读音 /vjuː/，类似于 **view**) 是一套用于构建用户界面的**渐进式框架**。

## Vue

- Vue树状表格tree-table-vue :wave:   [Github](https://github.com/lison16/tree-table-vue) ； 另外一款也不错 [vue-tree-grid](https://github.com/huanglong6828/vue-tree-grid)

- 高精度计算big.js :+1: [Github](https://github.com/MikeMcl/big.js)    [API文档](http://mikemcl.github.io/big.js)
- 

## 初识Vue             

### 如何使用Vue

直接下载并用`<script>`标签引入，`Vue`会被注册为一个全局变量。

```javascript
<!-- 开发环境版本，包含了有帮助的命令行警告 -->
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<!-- 生产环境版本，优化了尺寸和速度 -->
<script src="https://cdn.jsdelivr.net/npm/vue"></script>
```

> 注：在开发环境下不要使用压缩版本，不然你就失去了所有常见错误相关的警告!

### 声明式渲染

```vue
<!DOCTYPE html>
<html>

<head>
    <title>Vue学习示例01</title>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
</head>

<body>
    <div id="app">
        {{ message }}
    </div>
    <script type="text/javascript">
    var app = new Vue({
        el: '#app',
        data: {
            message: 'Hello Vue!'
        }
    })
    </script>
</body>

</html>
```

浏览器中打开该html文件，然后在控制台输入app.message='Hello Javascript'，页面上的文字也会跟着变化。

### 指令

Vue中指令带有前缀`v-`, 以表示它们是 Vue 提供的特殊特性。

- `v-bind` - 用来绑定数据和属性以及表达式，缩写为`:`

```vue
<div id="app-2">
  <span v-bind:title="message">
    鼠标悬停几秒钟查看此处动态绑定的提示信息！
  </span>
</div>
```

- `v-if`

```vue
<div id="app-3">
  <p v-if="seen">现在你看到我了</p>
</div>
```

- v-for`

```vue
<div id="app-4">
  <ol>
    <li v-for="todo in todos">
      {{ todo.text }}
    </li>
  </ol>
</div>
```

- `v-on` - 添加一个事件监听器, 
```vue
<div id="app-5">
  <p>{{ message }}</p>
  <button v-on:click="reverseMessage">逆转消息</button>
</div>

<script>
    var app5 = new Vue({
      el: '#app-5',
      data: {
        message: 'Hello Vue.js!'
      },
      methods: {
        reverseMessage: function () {
          this.message = this.message.split('').reverse().join('')
        }
      }
    })
</script>
```

- `v-model` - 实现**表单**输入和应用状态之间的双向绑定，在表单元素外使用不起作用

```vue
<div id="app-6">
  <p>{{ message }}</p>
  <input v-model="message">
</div>

<script>
    var app6 = new Vue({
      el: '#app-6',
      data: {
        message: 'Hello Vue!'
      }
    })
</script>
```



### 组件化构建

大型应用都是由一个个小组件构建而成

![](<https://cn.vuejs.org/images/components.png>)

```vue
<div id="app-7">
  <ol>
    <todo-item
      v-for="item in groceryList"
      v-bind:todo="item"
      v-bind:key="item.id"
    ></todo-item>
  </ol>
</div>

<script>
// 注册组件
Vue.component('todo-item', {
  props: ['todo'],
  template: '<li>{{ todo.text }}</li>'
})

var app7 = new Vue({
  el: '#app-7',
  data: {
    groceryList: [
      { id: 0, text: '蔬菜' },
      { id: 1, text: '奶酪' },
      { id: 2, text: '随便其它什么人吃的东西' }
    ]
  }
})
</script>
```



## Vue实例

每个 Vue 应用都是通过用 `Vue` 函数创建一个新的 **Vue 实例**

```javascript
var vm = new Vue({
    data: {
      newTodoText: '',
      visitCount: 0,
      hideCompletedTodos: false,
      todos: [],
      error: null
	}
})
```

当一个 Vue 实例被创建时，它将 `data` 对象中的所有的属性加入到 Vue 的**响应式系统**中。当这些属性的值发生改变时，视图将会产生“响应”，即匹配更新为新的值。

### 实例生命周期函数

```javascript
new Vue({
  data: {
    a: 1
  },
  created: function () {
    // `this` 指向 vm 实例
    console.log('a is: ' + this.a)
  }
})
```

每个 Vue 实例在被创建时都要经过一系列的初始化过程——例如，需要设置数据监听、编译模板、将实例挂载到 DOM 并在数据变化时更新 DOM 等。同时在这个过程中也会运行一些叫做**生命周期钩子**的函数，这给了用户在不同阶段添加自己的代码的机会。生命周期钩子的 `this` 上下文指向调用它的 Vue 实例

> 不要在选项属性或回调上使用[箭头函数](https://developer.mozilla.org/zh-CN/docs/Web/JavaScript/Reference/Functions/Arrow_functions)，比如 `created: () => console.log(this.a)` 或 `vm.$watch('a', newValue => this.myMethod())`。因为箭头函数并没有 `this`，`this`会作为变量一直向上级词法作用域查找，直至找到为止，

![](<https://cn.vuejs.org/images/lifecycle.png>)

## 模版语法

### 插值

```html
<span>Message: {{ msg }}</span>
```

Mustache 标签将会被替代为对应数据对象上 `msg` 属性的值。无论何时，绑定的数据对象上 `msg`属性发生了改变，插值处的内容都会更新。其中, `msg`也可以替换成任意的`Javascript`表达式

### 指令

指令 (Directives) 是带有 `v-` 前缀的特殊特性。指令的职责是，当表达式的值改变时，将其产生的连带影响，响应式地作用于 DOM。

```vue
<p v-if="seen">现在你看到我了</p>
// 指令能够接收一个“参数”
<a v-bind:href="url">...</a>
// 监听 DOM 事件
<a v-on:click="doSomething">...</a>
// 可以用方括号括起来的 JavaScript 表达式作为一个指令的参数
<a v-bind:[href]="url"> ... </a>
// 等价于 v-on:focus
<a v-on:[focus]="doSomething"> ... </a>
```



#### `v-bind`

用来绑定数据和属性以及表达式，缩写为`:`

```vue
<div id="app-2">
  <span v-bind:title="message">
    鼠标悬停几秒钟查看此处动态绑定的提示信息！
  </span>
</div>
```

#### `v-if`

```vue
<div id="app-3">
  <p v-if="seen">现在你看到我了</p>
</div>
```

也可使用`v-else`添加else块 (`v-else` 元素必须紧跟在带 `v-if` 或者 `v-else-if` 的元素的后面，否则它将不会被识别。)

```html
<h1 v-if="awesome">Vue is awesome!</h1>
<h1 v-else>Oh no 😢</h1>
```

2.1.0新增`v-else-if`

```html
<div v-if="type === 'A'">
  A
</div>
<div v-else-if="type === 'B'">
  B
</div>
<div v-else-if="type === 'C'">
  C
</div>
<div v-else>
  Not A/B/C
</div>
```

使用`key`管理可复用的元素

`v-show`与`v-if`用法大致相同，不同的是

- 带有 `v-show` 的元素始终会被渲染并保留在 DOM 中。`v-show` 只是简单地切换元素的 CSS 属性 `display`。
- `v-if` 是“真正”的条件渲染，它会确保在切换过程中条件块内的事件监听器和子组件适当地被销毁和重建。
- `v-show` 不支持 `<template>` 元素，也不支持 `v-else`。

因此，如果需要非常频繁地切换，则使用 `v-show` 较好；如果在运行时条件很少改变，则使用 `v-if` 较好。

> 注： **不推荐**同时使用 `v-if` 和 `v-for`。当 `v-if` 与 `v-for` 一起使用时，`v-for` 具有比 `v-if` 更高的优先级。

#### `v-for`

**`v-for`使用一个数组。**其中`items`是源数据数组，`item`是被迭代的数组元素的别名。`in`可以用`of`代替

建议尽可能在使用 `v-for` 时提供 `key` attribute

```html
<ul id="example-1">
  <li v-for="item in items" :key="item.id">
    {{ item.message }}
  </li>
</ul>

<!--支持可选的第二个参数-->
<ul id="example-2">
  <li v-for="(item, index) in items">
    {{ index }} - {{ item.message }}
  </li>
</ul>
```

**`v-for`使用对象。**可以用户遍历对象的属性，同样的，可以有一个可选的第二参数：属性名，还可以第三个参数作为索引

```html
<div v-for="(value, name, index) in object">
  {{ index }}. {{ name }}: {{ value }}
</div>
```

**数组更新检测**

变异方法

- `push()`
- `pop()`
- `shift()`
- `unshift()`
- `splice()`
- `sort()`
- `reverse()`

显示过滤/排序后的结果
可以创建一个计算属性，来返回过滤后或排序后的数组


#### `v-on`

添加一个事件监听器

```vue
<div id="app-5">
  <p>{{ message }}</p>
  <button v-on:click="reverseMessage">逆转消息</button>
</div>

<script>
    var app5 = new Vue({
      el: '#app-5',
      data: {
        message: 'Hello Vue.js!'
      },
      methods: {
        reverseMessage: function () {
          this.message = this.message.split('').reverse().join('')
        }
      }
    })
</script>
```

有时也需要在内联语句处理器中访问原始的 DOM 事件。可以用特殊变量 `$event` 把它传入方法

```vue
<button v-on:click="warn('Form cannot be submitted yet.', $event)">
  Submit
</button>

<script>
// ...
methods: {
  warn: function (message, event) {
    // 现在我们可以访问原生事件对象
    if (event) event.preventDefault()
    alert(message)
  }
}
</script>
```

`v-on` 提供了**事件修饰符**，修饰符是由点开头的指令后缀来表示的。

- `.stop`
- `.prevent`
- `.capture`
- `.self`
- `.once`
- `.passive`

```html
<!-- 阻止单击事件继续传播 -->
<a v-on:click.stop="doThis"></a>

<!-- 提交事件不再重载页面 -->
<form v-on:submit.prevent="onSubmit"></form>

<!-- 修饰符可以串联 -->
<a v-on:click.stop.prevent="doThat"></a>

<!-- 只有修饰符 -->
<form v-on:submit.prevent></form>

<!-- 添加事件监听器时使用事件捕获模式 -->
<!-- 即元素自身触发的事件先在此处理，然后才交由内部元素进行处理 -->
<div v-on:click.capture="doThis">...</div>

<!-- 只当在 event.target 是当前元素自身时触发处理函数 -->
<!-- 即事件不是从内部元素触发的 -->
<div v-on:click.self="doThat">...</div>
```

**按键修饰符**

Vue 允许为 `v-on` 在监听键盘事件时添加按键修饰符

```html
<!-- 只有在 `key` 是 `Enter` 时调用 `vm.submit()` -->
<input v-on:keyup.enter="submit">
```

#### `v-model`

实现**表单**输入和应用状态之间的双向绑定，在表单元素外使用不起作用

```vue
<div id="app-6">
  <p>{{ message }}</p>
  <input v-model="message">
</div>

<script>
    var app6 = new Vue({
      el: '#app-6',
      data: {
        message: 'Hello Vue!'
      }
    })
</script>
```

`v-model` 会忽略所有表单元素的 `value`、`checked`、`selected` 特性的初始值而总是将 Vue 实例的数据作为数据来源。你应该通过 JavaScript 在组件的 `data` 选项中声明初始值。

**修饰符**

* `.lazy`

在默认情况下，`v-model` 在每次 `input` 事件触发后将输入框的值与数据进行同步 (除了[上述](https://cn.vuejs.org/v2/guide/forms.html#vmodel-ime-tip)输入法组合文字时)。你可以添加 `lazy` 修饰符，从而转变为使用 `change` 事件进行同步

```html
<!-- 在“change”时而非“input”时更新 -->
<input v-model.lazy="msg" >
```

* `.number`

自动将用户的输入值转为数值类型

```html
<input v-model.number="age" type="number">
```

* `.trim`

```html
<input v-model.trim="msg">
```



## 计算属性和侦听器

### 计算属性

```vue
<div id="example">
  <p>Original message: "{{ message }}"</p>
  <p>Computed reversed message: "{{ reversedMessage }}"</p>
</div>

<script>
    var vm = new Vue({
      el: '#example',
      data: {
        message: 'Hello'
      },
      computed: {
        // 计算属性的 getter
        reversedMessage: function () {
          // `this` 指向 vm 实例
          return this.message.split('').reverse().join('')
        }
      }
    })
</script>
```

当 `vm.message` 发生改变时，所有依赖 `vm.reversedMessage` 的绑定也会更新。

**计算属性与方法的区别**

**计算属性是基于它们的响应式依赖进行缓存的**。只在相关响应式依赖发生改变时它们才会重新求值。

而方法是每当触发重新渲染时，调用方法将**总会**再次执行函数。

计算属性默认只有 getter ，不过在需要时也可以提供一个 setter，当对计算属性进行赋值时，会调用setter方法

```javascript
// ...
computed: {
  fullName: {
    // getter
    get: function () {
      return this.firstName + ' ' + this.lastName
    },
    // setter
    set: function (newValue) {
      var names = newValue.split(' ')
      this.firstName = names[0]
      this.lastName = names[names.length - 1]
    }
  }
}
// ...
```



### 侦听器

当需要在数据变化时执行异步或开销较大的操作时, 可以使用侦听器。

```vue
<div id="watch-example">
  <p>
    Ask a yes/no question:
    <input v-model="question">
  </p>
  <p>{{ answer }}</p>
</div>

<script src="https://cdn.jsdelivr.net/npm/axios@0.12.0/dist/axios.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/lodash@4.13.1/lodash.min.js"></script>
<script>
var watchExampleVM = new Vue({
  el: '#watch-example',
  data: {
    question: '',
    answer: 'I cannot give you an answer until you ask a question!'
  },
  watch: {
    // 如果 `question` 发生改变，这个函数就会运行
    question: function (newQuestion, oldQuestion) {
      this.answer = 'Waiting for you to stop typing...'
      this.debouncedGetAnswer()
    }
  },
  created: function () {
    // `_.debounce` 是一个通过 Lodash 限制操作频率的函数。
    // 在这个例子中，我们希望限制访问 yesno.wtf/api 的频率
    // AJAX 请求直到用户输入完毕才会发出。想要了解更多关于
    // `_.debounce` 函数 (及其近亲 `_.throttle`) 的知识，
    // 请参考：https://lodash.com/docs#debounce
    this.debouncedGetAnswer = _.debounce(this.getAnswer, 500)
  },
  methods: {
    getAnswer: function () {
      if (this.question.indexOf('?') === -1) {
        this.answer = 'Questions usually contain a question mark. ;-)'
        return
      }
      this.answer = 'Thinking...'
      var vm = this
      axios.get('https://yesno.wtf/api')
        .then(function (response) {
          vm.answer = _.capitalize(response.data.answer)
        })
        .catch(function (error) {
          vm.answer = 'Error! Could not reach the API. ' + error
        })
    }
  }
})
</script>
```

## Class与Style绑定

### 绑定HTML Class - `v-bind:class`

我们可以传给 `v-bind:class` 一个对象，以动态地切换 class

比较常用的三元表达式

```vue
<div v-bind:class="[isActive ? activeClass : '', errorClass]"></div>
```

示例

```vue
<div
  class="static"
  v-bind:class="{ active: isActive, 'text-danger': hasError }"
></div>

<script>
data: {
  isActive: true,
  hasError: false
}
</script>

// 结果渲染为
<div class="static active"></div>
// 当 isActive 或者 hasError 变化时，class 列表将相应地更新。



```

以上语法可进一步简化为

```vue
<div v-bind:class="classObject"></div>

<script>
data: {
  classObject: {
    active: true,
    'text-danger': false
  }
}
</script>
```

也可使用计算属性

```vue
<div v-bind:class="classObject"></div>

<script>
data: {
  isActive: true,
  error: null
},
computed: {
  classObject: function () {
    return {
      active: this.isActive && !this.error,
      'text-danger': this.error && this.error.type === 'fatal'
    }
  }
}
</script>
```

当在一个自定义组件上使用 `class` 属性时，这些类将被**添加**到该组件的根元素上面。这个元素上已经存在的类不会被覆盖。

### 绑定内联样式 - `v-bind:style`

```vue
<div v-bind:style="{ color: activeColor, fontSize: fontSize + 'px' }"></div>

<script>
data: {
    activeColor: 'red',
    fontSize: 30
}
</script>
```

或者直接绑定对象

```vue
<div v-bind:style="styleObject"></div>

<script>
data: {
  styleObject: {
    color: 'red',
    fontSize: '13px'
  }
}
</script>
```



















