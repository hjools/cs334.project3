
    size 50 50
    begin
       cat haru 2 19 north ;
        mouse noel 37 15 east ;
        hole 0 0 ;
        repeat 5
            clockwise haru ;
            move haru 2 ;
            clockwise noel ;
            move noel 3 ;
        end ;
        repeat 2
            clockwise noel ;
        end ;
        move noel 10 ;
        move haru 20 ;
        mouse moo 10 36 west ;
        repeat 7
            move moo ;
        end ;
        clockwise moo ;
        move haru ;
        repeat 3
            clockwise haru ;
            move moo ;
        end ;
    halt