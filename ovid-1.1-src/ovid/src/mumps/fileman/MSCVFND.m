MSCVFND	; MSC/JDA - OVID FileMan FIND ; SEP 28, 2010 22:00:00
 ;;1.0;OpenVista Interface Domain;**1500**;May 14, 2009
 ;
RUN(REPLY,RES)	;
 N THIS
 D CNSTRCT(.THIS)
 N TARGET
 D PROCESS(.THIS,.RES)
 I $G(THIS("ERROR"))'="" D ADDPROP^MSCVRES(.REPLY,"ERROR",THIS("ERROR")) Q
 D:$G(THIS("SAFE")) SAFE(.THIS)
 I $G(THIS("ERROR"))'="" D ADDPROP^MSCVRES(.REPLY,"ERROR",THIS("ERROR")) Q
 ; Call Fileman
 D FIND^DIC(THIS("FILE"),THIS("IENS"),THIS("FIELDS"),THIS("FLAGS"),THIS("IDXVALUE"),THIS("NUMBER"),THIS("IDXNAME"),THIS("SCREEN"),,"TARGET")
 D BLDREPLY^MSCVQRY(.THIS,.TARGET,.REPLY)
 Q
PROCESS(THIS,RES)	;
 F  Q:('$$NEXTPROP^MSCVRES(.RES))!($G(THIS("ERROR"))'="")  D
 . D PROCPROP(.THIS,.RES)
 . Q 
 Q
PROCIDX(THIS,RES)	;
 N PROPNAME
 F  Q:'$$NEXTPROP^MSCVRES(.RES)  D
 . S PROPNAME=$$GETPROP^MSCVRES(.RES)
 . I PROPNAME="NAME" S THIS("IDXNAME")=$$GETVAL^MSCVRES(.RES) Q
 . I PROPNAME="VALUE" S THIS("IDXVALUE")=$$GETVAL^MSCVRES(.RES) Q
 . Q
 Q
PROCPROP(THIS,RES)	;
 N PROPNAME
 S PROPNAME=$$GETPROP^MSCVRES(.RES)
 I PROPNAME="INDEX" D PROCIDX(.THIS,.RES) Q
 D PROCPROP^MSCVQRY(.THIS,.RES)
 Q
CNSTRCT(THIS)	;
 D CNSTRCT^MSCVQRY(.THIS)
 S THIS("FLAGS")=THIS("FLAGS")_"X"
 S THIS("IDXNAME")="",THIS("IDXVALUE")=""
 Q
SAFE(THIS)	;
 N TRG,I,PCNUM,PC,FIELD,FNUM,DONE,RESULT
 S FIELDS=THIS("FIELDS")
 D FIND^DIC(21455,,"@;1;2;3I","Q",THIS("FILE"),,"C",,,"TRG")
 Q:'$P(TRG("DILIST",0),"^",1)
 S RESULT="@"
 F PCNUM=2:1:$L(FIELDS,";") D
 . S PC=$P(FIELDS,";",PCNUM)
 . S FNUM=+PC
 . S I="",DONE=0 F  S I=$O(TRG("DILIST","ID",I)) Q:I=""  D  Q:DONE
 . . I TRG("DILIST","ID",I,3)="S" S THIS("ERROR")="No data due to safe mode.",DONE=1 Q
 . . I TRG("DILIST","ID",I,2)=FNUM D  S DONE=1
 . . . Q:TRG("DILIST","ID",I,3)="N"
 . . . I TRG("DILIST","ID",I,3)="I" S:PC'["I" PC=PC_"I" S RESULT=RESULT_";"_PC Q
 . . . Q
 . S:'DONE RESULT=RESULT_";"_PC
 . Q
 S THIS("FIELDS")=RESULT
 Q
