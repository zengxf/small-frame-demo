var tClass = 'cn.simple.test.script.js.JsFunTest';

var fun1 = function(name) {
	print('Hi there from Javascript, ' + name);
	return "greetings from javascript";
};
var fun2 = function(object) {
	print(object.toString());
	print("JS Class Definition: " + Object.prototype.toString.call(object));
};

function fun3() {
	var MyJavaClass = Java.type(tClass);
	var result = MyJavaClass.fun1('John Doe');
	print(result);

	MyJavaClass.fun2(123);
	// class java.lang.Integer
	MyJavaClass.fun2(49.99);
	// class java.lang.Double
	MyJavaClass.fun2(true);
	// class java.lang.Boolean
	MyJavaClass.fun2("hi there")
	// class java.lang.String
	MyJavaClass.fun2(new Number(23));
	// class jdk.nashorn.internal.objects.NativeNumber
	MyJavaClass.fun2(new Date());
	// class jdk.nashorn.internal.objects.NativeDate
	MyJavaClass.fun2(new RegExp());
	// class jdk.nashorn.internal.objects.NativeRegExp
	MyJavaClass.fun2({
		foo : 'bar'
	});
	// class jdk.nashorn.internal.scripts.JO4

	MyJavaClass.fun3({
		foo : 'bar',
		bar : 'foo'
	});
}

function Person(firstName, lastName) {
	this.firstName = firstName;
	this.lastName = lastName;
	this.getFullName = function() {
		return this.firstName + " " + this.lastName;
	}
}

function fun4() {
	var person1 = new Person("Peter", "Parker");
	var MyJavaClass = Java.type(tClass);
	MyJavaClass.fun4(person1);
}

function testJavaArray() {
	var IntArray = Java.type("int[]");
	var array = new IntArray(5);
	array[0] = 5;
	array[1] = 4;
	array[2] = 3;
	array[3] = 2;
	array[4] = 1;
	try {
		array[5] = 23;
	} catch (e) {
		print(e.message); // Array index out of range: 5
	}
	array[0] = "17";
	print(array[0]); // 17
	array[0] = "wrong type";
	print(array[0]); // 0
	array[0] = "17.3";
	print(array[0]); // 17
}

function testJavaCollection() {
	var ArrayList = Java.type('java.util.ArrayList');
	var list = new ArrayList();
	list.add('a');
	list.add('b');
	list.add('c');
	// for each (var el in list) print(el); // a, b, c
}

function testJavaMap() {
	var map = new java.util.HashMap();
	map.put('foo', 'val1');
	map.put('bar', 'val2');
	// for each (var e in map.keySet()) print(e); // foo, bar
	// for each (var e in map.values()) print(e); // val1, val2
}

// 鍙互鍦ㄤ换浣曟帴鍙條ambda琛ㄨ揪寮忕殑鍦版柟浣跨敤鍑芥暟瀛楅潰鍊笺��
function testLambda() {
	var list2 = new java.util.ArrayList();
	list2.add("ddd2");
	list2.add("aaa2");
	list2.add("bbb1");
	list2.add("aaa1");
	list2.add("bbb3");
	list2.add("ccc");
	list2.add("bbb2");
	list2.add("ddd1");
	list2.stream().filter(function(el) {
		return el.startsWith("aaa");
	}).sorted().forEach(function(el) {
		print(el);
	});
	// aaa1, aaa2
}

function testExtend() {
	var Runnable = Java.type('java.lang.Runnable');
	var Thread = Java.type('java.lang.Thread');

	var Printer = Java.extend(Runnable, {
		run : function() {
			print('printed from a separate thread');
		}
	});

	var t1 = new Thread(new Printer());
	t1.start();

	new Thread(function() {
		print('printed from another thread');
	}).start();

	print('t1 thread name: ' + t1.getName());
}

function testMethod() {
	var System = Java.type('java.lang.System');
	System.out.println(10); // 10
	System.out["println"](11.0); // 11.0
	System.out["println(double)"](12); // 12.0
}

function testImport() {
	// 瀵煎叆澶氫釜Java鍖�
	var imports = new JavaImporter(java.io, java.lang);
	// 鎵�鏈夎瀵煎叆鍖呯殑绫绘枃浠堕兘鍙互鍦� with 璇彞鐨勫眬閮ㄥ煙涓闂埌
	with (imports) {
		var file = new File(__FILE__);
		System.out.println(file.getAbsolutePath());
	}
}

function testTransform() {
	// 涓�浜涚被浼� java.util 鐨勫寘鍙互涓嶄娇鐢� java.type 鎴� JavaImporter 鐩存帴璁块棶
	var list = new java.util.ArrayList();
	list.add("s1");
	list.add("s2");
	list.add("s3");
	print(list);

	var jsArray = Java.from(list);
	print(jsArray); // s1,s2,s3
	print(Object.prototype.toString.call(jsArray)); // [object Array]

	var javaArray = Java.to([ 3, 5, 7, 11 ], "int[]");
	print(javaArray);
	try {
		print(new cn.simple.test.script.js.JsFunTest());
	} catch (e) {
		print("鑷畾涔夌殑绫讳笉鑳界洿鎺ヨ闂�!", e);
	}
}

function testSuper() {
	var SuperRunner = Java.type(tClass);
	var Runner = Java.extend(SuperRunner);
	// var runner = new Runner() {
	// run: function() {
	// // 璁块棶瓒呯被
	// Java.super(runner).run();
	// print('on my run');
	// }
	// }
	// runner.run();
}

function testLoad() {
	var file = new java.io.File(
			'src/cn/simple/test/script/js/js-folder/script2.js');

	// 鍙橀噺鍚嶇О浜掔浉鍐茬獊鏃讹紝鑴氭湰鐨勫姞杞藉彲鑳戒細浣夸綘鐨勪唬鐮佸穿婧冦�� 杩欎竴闂鍙互閫氳繃鎶婅剼鏈枃浠跺姞杞藉埌鏂扮殑鍏ㄥ眬涓婁笅鏂囨潵缁曡繃
	loadWithNewGlobal(file.getAbsolutePath());

	load(file.getAbsolutePath());
	print('test js file: ', file.getAbsolutePath());
	print('test name: ', name);
}