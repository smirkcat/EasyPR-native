{
    "targets": [{
        "target_name": "easyprnode",
        "conditions": [[
            'OS=="win"', {
                "libraries": [
              	        "../NativeEasyPR/x64/Release/easyprpy.lib",
                         "$(OPENCV310)/x64/vc12/lib/opencv_world300.lib"],
                'defines': ['']
            
            }
        ], [
            'OS=="linux"', {
                'cflags': ['-std=c++11','-fexceptions'],
                'cflags_cc': ['-std=c++11','-fexceptions'],
                "libraries": [
              		"../NativeEasyPR/Release/easyprpy.a"
            ]
            }
        ]],
        "sources": [
            "src/js.cpp",
            "src/jsobject.cpp",
            "../NativeEasyPR/src/process.cpp"
        ],
        'include_dirs': [
        	"include",
        	"../EasyPR/include",
        	"../NativeEasyPR/include",
        	"$(OPENCV310)/include"
        	]
    }]

}