extern crate libc;
extern crate libloading as lib;
use lib::Symbol;
use libc::c_long;
use libc::c_int;
use libc::c_char;
use libc::c_void;
use std::ffi::CString;
use std::io;
use std::io::prelude::*;
use std::fs::File;

// #[link(name = "easyprexport")]
// extern {
//     fn init(arg1: *mut c_char) -> c_long; // 声明ffi函数
//     fn plateRecognize(arg1: c_long, arg2: *mut c_char, arg3: c_int) -> *mut c_char;
//     fn deleteptr(arg1: c_long) -> c_void; // 声明ffi函数
// }


fn main() {
    // println!("Hello, world!");
    // let x = 5;
    // let raw = &x as *const i32;
    // let points_at = unsafe { *raw };
    // println!("raw points at {}", points_at);

    let modelpath = CString::new("../model").unwrap();
    let libs = lib::Library::new("easyprexport").unwrap();
    let mut f = File::open("test.jpg").unwrap();
    let metadata = f.metadata();
    let mut buffer = Vec::new();
    // read the whole file
    f.read_to_end(&mut buffer);
    let p = buffer.as_mut_ptr();
    let len = buffer.len();
    println!("图片长度 {}", len);
    
    unsafe {
        let init: Symbol<unsafe extern fn(arg1: *const c_char) -> c_long> =libs.get(b"init\0").unwrap();
        let ret=init(modelpath.as_ptr());
        println!("point {}", ret);
        let plateRecognize: Symbol<unsafe extern fn(arg1: c_long, arg2: *mut c_char, arg3: c_int) -> *mut c_char> =libs.get(b"plateRecognize\0").unwrap();
        let strre=plateRecognize(ret,p as *mut c_char,len as c_int);
        let strres=*strre;
        println!("point {}", strres);
        let deleteptr: Symbol <unsafe extern fn(arg1: c_long) -> c_void > =libs.get(b"deleteptr\0").unwrap();
        deleteptr(ret);
    }

}
