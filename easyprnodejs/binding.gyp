{
    "targets": [{
        "target_name": "easyprnode",
        "conditions": [[
            'OS=="win"', {
                "libraries": [
              	    "<(module_root_dir)/../NativeEasyPR/x64/Release/easyprcore.lib",
                    "$(OPENCV310)/x64/vc12/lib/opencv_world310.lib"
                ],
                'include_dirs': [
                    "$(OPENCV310)/include"
                ],
                "cflags": [
                    "-Wall"
                ],
                "defines": [
                    "WIN"
                ],
                "msvs_settings": {
                    "VCCLCompilerTool": {
                        "DisableSpecificWarnings": [ "4530", "4506", "4244" ],
                        
                    },
                }
            }
        ], [
            'OS=="linux"', {
                'cflags': [
                    '-std=c++11',
                    '-fexceptions',
                    "<!@(node find-opencv.js --cflags)",
                    "-Wall"
                    ],
                'cflags_cc': ['-std=c++11','-fexceptions'],
                "libraries": [
              		"<(module_root_dir)/../NativeEasyPR/build/libeasyprcore.a",
                    "<!@(node find-opencv.js --libs)"
                ]
            }
        ],[ # cflags on OS X are stupid and have to be defined like this
            'OS=="mac"', {
                "xcode_settings": {
                    "OTHER_CFLAGS": [
                    "-mmacosx-version-min=10.7",
                    "-std=c++11",
                    "-stdlib=libc++",
                    "<!@(node find-opencv.js --cflags)",
                    ],
                "GCC_ENABLE_CPP_RTTI": "YES",
                "GCC_ENABLE_CPP_EXCEPTIONS": "YES"
            },
                "libraries": [
                    "<(module_root_dir)/../NativeEasyPR/build/easyprcore.a",
                    "<!@(node find-opencv.js --libs)"
                ]
          }
        ]],
        'cflags_cc!': [ '-fno-rtti' ],
        "sources": [
            "src/js.cpp",
            "src/jsobject.cpp",
            "../NativeEasyPR/src/process.cpp"
        ],
        'include_dirs': [
        	"include",
        	"../EasyPR/include",
        	"../NativeEasyPR/include"
        ]
    }],
    'configurations': {
        'Debug': {
            "conditions": [
            ['OS=="linux"', {
              "cflags": ["-coverage"],
              "ldflags": ["-coverage"]
            }],
            ['OS=="mac"', {
              "xcode_settings": {
                "OTHER_CFLAGS": [
                  "-fprofile-arcs -ftest-coverage",
                ],
                "OTHER_LDFLAGS": [
                  "--coverage"
                ]
              }
            }],
            ['OS=="windows"', {
              "msvs_settings": {
              "VCCLCompilerTool": {
                'ExceptionHandling': 1, # /EHsc
                        'RuntimeTypeInfo': 'true', # /GR
                        'RuntimeLibrary': '2' # /MD
              },
            }
            }]

        ]
        },'Release': {
            "conditions": [
            ['OS=="windows"', {
              "msvs_settings": {
              "VCCLCompilerTool": {
                'ExceptionHandling': 1, # /EHsc
                        'RuntimeTypeInfo': 'true', # /GR
                        'RuntimeLibrary': '2' # /MD
              },
            }
            }]
        ]
        }
        }
}