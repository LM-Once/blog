[nodes] 
 [tu] 
   [attr:log] 
   [id:0c2f154f] 
   [x:877] 
   [y:-184]  
   [shape:circle-node] 
   [level:I] 
   [content:TURNING_OFF] 
   [priority:1] 
   [color:#FA8C16] 
   [index:12] 
   [size:72*72] 
   [type:node] 
   [tag:BL_off] 
   [label:AdapterState]
 [/tu] 
 [tu] 
   [attr:log] 
   [id:eb8817d4] 
   [x:883] 
   [y:-81] 
   [shape:circle-node] 
   [level:W] 
   [content:Calling startBrEdrCleanup] 
   [priority:1] 
   [color:#FA8C16] 
   [index:13] 
   [size:72*72] 
   [type:node] 
   [tag:BL_off] 
   [label:AdapterState] 
 [/tu] 
 [tu] 
   [attr:log] 
   [id:5215ec9a] 
   [x:877] 
   [y:24] 
   [shape:circle-node] 
   [level:I] 
   [content:BLE_ON] 
   [priority:1] 
   [color:#FA8C16] 
   [index:14] 
   [size:72*72] 
   [type:node] 
   [tag:BL_off] 
   [label:AdapterState] 
 [/tu] 
 [tu] 
   [attr:log] 
   [id:489200da] 
   [x:876] 
   [y:118] 
   [shape:circle-node] 
   [level:I] 
   [content:BLE_TURNING_OFF] 
   [priority:1] 
   [color:#FA8C16] 
   [index:15] 
   [size:72*72] 
   [type:node] 
   [tag:BL_off] 
   [label:AdapterState] 
 [/tu] 
 [tu] 
   [attr:time] 
   [id:4fae844c] 
   [x:875] 
   [y:192] 
   [shape:flow-circle] 
   [color:#FA8C16] 
   [index:16] 
   [size:55*55] 
   [type:node] 
   [label:8s] 
 [/tu] 
 [tu] 
   [attr:log] 
   [id:343c36be] 
   [x:769] 
   [y:260] 
   [shape:circle-node] 
   [level:I] 
   [content:OFF] 
   [priority:1] 
   [color:#FA8C16] 
   [index:17] 
   [size:72*72] 
   [type:node] 
   [tag:BL_off] 
   [label:AdapterState] 
 [/tu] 
 [tu] 
   [attr:error] 
   [id:9dd3a1b3] 
   [x:955] 
   [y:241] 
   [shape:circle-node] 
   [level:E] 
   [content:BLE_TURNING_OFF] 
   [priority:1] 
   [color:red] 
   [index:18] 
   [size:55*55] 
   [type:node] 
   [tag:BL_off] 
   [label:AdapterState] 
 [/tu] 
[/nodes], 
[edges] 
 [tu] 
   [source:0c2f154f] 
   [target:eb8817d4] 
   [shape:flow-smooth] 
   [id:737c0795] 
   [index:0] 
   [sourceAnchor:2] 
   [targetAnchor:0] 
 [/tu] 
 [tu] 
   [source:eb8817d4] 
   [target:5215ec9a] 
   [shape:flow-smooth] 
   [id:5b26c7f8] 
   [index:1] 
   [sourceAnchor:2] 
   [targetAnchor:0] 
 [/tu] 
 [tu] 
   [source:5215ec9a] 
   [target:489200da] 
   [shape:flow-smooth] 
   [id:227e78dd] 
   [index:2] 
   [sourceAnchor:2] 
   [targetAnchor:0] 
 [/tu] 
 [tu] 
   [source:489200da] 
   [target:4fae844c] 
   [shape:flow-smooth] 
   [id:83d4c236] 
   [index:9] 
   [sourceAnchor:2] 
   [targetAnchor:0] 
 [/tu] 
 [tu] 
   [source:4fae844c] 
   [target:343c36be] 
   [shape:flow-smooth] 
   [id:10402021] 
   [index:10] 
   [sourceAnchor:2] 
   [targetAnchor:1] 
 [/tu] 
 [tu] 
   [source:4fae844c] 
   [target:9dd3a1b3] 
   [shape:flow-smooth] 
   [id:e1616a86] 
   [index:11] 
   [sourceAnchor:1] 
   [targetAnchor:3] 
 [/tu] 
[/edges] 
