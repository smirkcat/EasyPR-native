{
    "targets": [{
        "target_name": "easyprnode",
        "conditions": [[
            'OS=="win"', {
                "libraries": [
              	        "<(module_root_dir)/../NativeEasyPR/x64/Release/easyprcore.lib",
                         "$(OPENCV310)/x64/vc12/lib/opencv_world310.lib"],
                'defines': ['']
            
            }
        ], [
            'OS=="linux"', {
                'cflags': ['-std=c++11','-fexceptions'],
                'cflags_cc': ['-std=c++11','-fexceptions'],
                "libraries": [
              		"../NativeEasyPR/Release/easyprcore.a"
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