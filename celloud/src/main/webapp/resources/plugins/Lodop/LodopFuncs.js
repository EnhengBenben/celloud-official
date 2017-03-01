var CreatedOKLodop7766=null;

//====判断是否需要安装CLodop云打印服务器:====
function needCLodop(){
    try{
    	var ua=navigator.userAgent;
    	if (ua.match(/Windows\sPhone/i) !=null) return true;
    	if (ua.match(/Android/i) != null) return true;
    	if (ua.match(/Edge\D?\d+/i) != null) return true;
    	if (ua.match(/QQBrowser/i) != null) return false;
    	var verTrident=ua.match(/Trident\D?\d+/i);
    	var verIE=ua.match(/MSIE\D?\d+/i);
    	var verOPR=ua.match(/OPR\D?\d+/i);
    	var verFF=ua.match(/Firefox\D?\d+/i);
    	var x64=ua.match(/x64/i);
    	if ((verTrident==null)&&(verIE==null)&&(x64!==null)) 
    		return true; else
    	if ( verFF !== null) {
    		verFF = verFF[0].match(/\d+/);
    		if ( verFF[0] >= 42 ) return true;
    	} else 
    	if ( verOPR !== null) {
    		verOPR = verOPR[0].match(/\d+/);
    		if ( verOPR[0] >= 32 ) return true;
    	} else 
    	if ((verTrident==null)&&(verIE==null)) {
    		var verChrome=ua.match(/Chrome\D?\d+/i);		
    		if ( verChrome !== null ) {
    			verChrome = verChrome[0].match(/\d+/);
    			if (verChrome[0]>=42) return true;
    		};
    	};
        return false;
    } catch(err) {return true;};
    return false;
};

//====页面引用CLodop云打印必须的JS文件：====
if (needCLodop()) {
	var head = document.head || document.getElementsByTagName("head")[0] || document.documentElement;
	var oscript = document.createElement("script");
	oscript.src ="https://localhost:8443/CLodopfuncs.js?priority=1";
	head.insertBefore( oscript,head.firstChild );
//	//本机云打印的后补端口8001：
//	oscript = document.createElement("script");
//	oscript.src ="http://localhost:8001/CLodopfuncs.js?priority=2";
//	head.insertBefore( oscript,head.firstChild );
};

//====获取LODOP对象的主过程：====
function getLodop(oOBJECT,oEMBED){
//    var strHtmInstall="<font>打印控件未安装!点击这里<a href='"+window.CONTEXT_PATH+"/plugins/Lodop/install_lodop32.exe' target='_self'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
//    var strHtmUpdate="<font>打印控件需要升级!点击这里<a href='"+window.CONTEXT_PATH+"/plugins/Lodop/install_lodop32.exe' target='_self'>执行升级</a>,升级后请重新进入。</font>";
//    var strHtm64_Install="<font>打印控件未安装!点击这里<a href='"+window.CONTEXT_PATH+"/plugins/Lodop/install_lodop64.exe' target='_self'>执行安装</a>,安装后请刷新页面或重新进入。</font>";
    var strHtm64_Update="<font>打印控件需要升级!点击这里<a href='"+window.CONTEXT_PATH+"/plugins/Lodop/CLodop_Setup_for_Win32NT_https_2.094.exe' target='_self'>执行升级</a>,升级后请重新进入。</font>";
//    var strHtmFireFox="<font>（注意：如曾安装过Lodop旧版附件npActiveXPLugin,请在【工具】->【附加组件】->【扩展】中先卸它）</font>";
    var strHtmChrome="<font>(如果此前正常，仅因浏览器升级或重安装而出问题，需点击这里<a href='"+window.CONTEXT_PATH+"/plugins/Lodop/CLodop_Setup_for_Win32NT_https_2.094.exe' target='_self'>重新安装</a>,安装后请刷新页面。）</font>";
    var strCLodopInstall="<font>CLodop云打印服务(localhost本地)未安装启动!点击这里<a href='"+window.CONTEXT_PATH+"/plugins/Lodop/CLodop_Setup_for_Win32NT_https_2.094.exe' target='_self'>执行安装</a>,安装后请刷新页面。</font>";
    var strCLodopUpdate="<font color='#FF00FF'>CLodop云打印服务需升级!点击这里<a href='"+window.CONTEXT_PATH+"/plugins/Lodop/CLodop_Setup_for_Win32NT_https_2.094.exe' target='_self'>执行升级</a>,升级后请刷新页面。</font>";
    var LODOP;
    try{
        var isIE = (navigator.userAgent.indexOf('MSIE')>=0) || (navigator.userAgent.indexOf('Trident')>=0);
        if (needCLodop()) {
          alert("needCLodop");
            try{ LODOP=getCLodop(); alert(getCLodop())} catch(err) {};
            if (!LODOP && document.readyState!=="complete") {alert("C-Lodop没准备好，请稍后再试！"); return;};
            if (!LODOP) {
          		 $.tips(strCLodopInstall,'请下载'); 
                 return;
            } else {
               if (CLODOP.CVERSION<"2.0.9.4") { 
                   $.tips(strCLodopUpdate,'请下载'); 
               };
               if (oEMBED && oEMBED.parentNode) oEMBED.parentNode.removeChild(oEMBED);
               if (oOBJECT && oOBJECT.parentNode) oOBJECT.parentNode.removeChild(oOBJECT);	
    	      };
        } else {
            var is64IE  = isIE && (navigator.userAgent.indexOf('x64')>=0);
            //=====如果页面有Lodop就直接使用，没有则新建:==========
            if (oOBJECT!=undefined || oEMBED!=undefined) {
                if (isIE) LODOP=oOBJECT; else  LODOP=oEMBED;
            } else if (CreatedOKLodop7766==null){
                LODOP=document.createElement("object");
                LODOP.setAttribute("width",0);
                LODOP.setAttribute("height",0);
                LODOP.setAttribute("style","position:absolute;left:0px;top:-100px;width:0px;height:0px;");
                if (isIE) LODOP.setAttribute("classid","clsid:2105C259-1E0C-4534-8141-A753534CB4CA");
                else LODOP.setAttribute("type","application/x-print-lodop");
                document.documentElement.appendChild(LODOP);
                CreatedOKLodop7766=LODOP;
             } else LODOP=CreatedOKLodop7766;
            //=====Lodop插件未安装时提示下载地址:==========
            if ((LODOP==null)||(typeof(LODOP.VERSION)=="undefined")) {
//                 if (navigator.userAgent.indexOf('Chrome')>=0)
//                   $.tips(strHtmChrome,'请下载'); 
//                 if (navigator.userAgent.indexOf('Firefox')>=0)
//                   $.tips(strHtmFireFox,'请下载'); 
//                 if (is64IE) document.write(strHtm64_Install); else
//                 if (isIE)   document.write(strHtmInstall);    else
                   $.tips(strHtmInstall,'请下载'); 
                 return LODOP;
            };
        };
//        if (LODOP.VERSION<"6.2.0.5") {
//            if (needCLodop())
//              $.tips(strCLodopUpdate,'请下载'); 
//            document.documentElement.innerHTML=strCLodopUpdate+document.documentElement.innerHTML; else
//            if (is64IE) $.tips(strHtm64_Update,'请下载'); /*document.write(strHtm64_Update);*/ else
//            if (isIE) $.tips(strHtmUpdate,'请下载');/*document.write(strHtmUpdate);*/ else
//              $.tips(strHtmUpdate,'请下载');
//              document.documentElement.innerHTML=strHtmUpdate+document.documentElement.innerHTML;
//            return LODOP;
//        };
        //===如下空白位置适合调用统一功能(如注册语句、语言选择等):===
        LODOP.SET_LICENSES("","214353657A794760E9D65580BF1CA193","C94CEE276DB2187AE6B65D56B3FC2848","214353657A794760E9D65580BF1CA193");
        //===========================================================
        return LODOP;
    } catch(err) {alert("getLodop出错:"+err);};
};

function printQRCode(sampleName,date){
  var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
  LODOP.PRINT_INIT("打印提取DNA二维码takenDNAS");
  LODOP.ADD_PRINT_BARCODE(0, 7, 20, 20, 'QRCode', sampleName);
  LODOP.NEWPAGEA();
  LODOP.ADD_PRINT_BARCODE(2, 7, 20, 20, 'QRCode', sampleName);
  LODOP.ADD_PRINT_TEXTA('sname',25,30,180,8,sampleName);
  LODOP.SET_PRINT_STYLEA('sname', 'FontSize', 6);
  LODOP.SET_PRINT_STYLEA('sname', 'Angle', -90);
  LODOP.ADD_PRINT_TEXTA('uname',25,22,180,8,window.username);
  LODOP.SET_PRINT_STYLEA('uname', 'FontSize', 6);
  LODOP.SET_PRINT_STYLEA('uname', 'Angle', -90);
  LODOP.ADD_PRINT_TEXTA('date',25,14,180,8,date);
  LODOP.SET_PRINT_STYLEA('date', 'FontSize', 6);
  LODOP.SET_PRINT_STYLEA('date', 'Angle', -90);
  LODOP.SET_PRINT_PAGESIZE(1, 90, 380, "");
  LODOP.PRINT();
}

function printQRCodeBatch(sampleNames,dates){
	var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
	LODOP.PRINT_INIT("打印提取DNA二维码takenDNAS");
	for(i in sampleNames){
		LODOP.NEWPAGE();
	    LODOP.ADD_PRINT_BARCODE(8, 5, 20, 20, 'QRCode', sampleNames[i]);
	    LODOP.NEWPAGE();
	    LODOP.ADD_PRINT_BARCODE(2, 5, 20, 20, 'QRCode', sampleNames[i]);
	    LODOP.ADD_PRINT_TEXTA('sname',25,27,180,8,sampleNames[i]);
	    LODOP.SET_PRINT_STYLEA('sname', 'FontSize', 6);
	    LODOP.SET_PRINT_STYLEA('sname', 'Angle', -90);
	    LODOP.ADD_PRINT_TEXTA('uname',25,18,180,8,window.username);
	    LODOP.SET_PRINT_STYLEA('uname', 'FontSize', 6);
	    LODOP.SET_PRINT_STYLEA('uname', 'Angle', -90);
	    LODOP.ADD_PRINT_TEXTA('date',25,11,180,8,dates[i]);
	    LODOP.SET_PRINT_STYLEA('date', 'FontSize', 6);
	    LODOP.SET_PRINT_STYLEA('date', 'Angle', -90);
	}
    LODOP.PRINT();
}
