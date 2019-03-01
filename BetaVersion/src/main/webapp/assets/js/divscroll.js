(function (F) {
    var G = false;
    var H = false;
    var I = false;
    var J = 5000;
    var K = 2000;
    var $ = F;

    function getScriptPath() {
        var a = document.getElementsByTagName('script');
        var b = a[a.length - 1].src.split('?')[0];
        return (b.split('/').length > 0) ? b.split('/').slice(0, -1).join('/') + '/' : ''
    }
    var L = getScriptPath();
    var M = (function () {
        return window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame || false
    })();
    var N = (function () {
        return window.cancelRequestAnimationFrame || window.webkitCancelRequestAnimationFrame || window.mozCancelRequestAnimationFrame || window.oCancelRequestAnimationFrame || window.msCancelRequestAnimationFrame || false
    })();
    var O = false;
    var P = function () {
        if (O) return O;
        var c = document.createElement('DIV');
        var d = {};
        d.haspointerlock = "pointerLockElement" in document || "mozPointerLockElement" in document || "webkitPointerLockElement" in document;
        d.isopera = ("opera" in window);
        d.isopera12 = (d.isopera && ("getUserMedia" in navigator));
        d.isie = (("all" in document) && ("attachEvent" in c) && !d.isopera);
        d.isieold = (d.isie && !("msInterpolationMode" in c.style));
        d.isie7 = d.isie && !d.isieold && (!("documentMode" in document) || (document.documentMode == 7));
        d.isie8 = d.isie && ("documentMode" in document) && (document.documentMode == 8);
        d.isie9 = d.isie && ("performance" in window) && (document.documentMode >= 9);
        d.isie10 = d.isie && ("performance" in window) && (document.documentMode >= 10);
        d.isie9mobile = /iemobile.9/i.test(navigator.userAgent);
        if (d.isie9mobile) d.isie9 = false;
        d.isie7mobile = (!d.isie9mobile && d.isie7) && /iemobile/i.test(navigator.userAgent);
        d.ismozilla = ("MozAppearance" in c.style);
        d.iswebkit = ("WebkitAppearance" in c.style);
        d.ischrome = ("chrome" in window);
        d.ischrome22 = (d.ischrome && d.haspointerlock);
        d.cantouch = ("ontouchstart" in document.documentElement) || ("ontouchstart" in window);
        d.hasmstouch = (window.navigator.msPointerEnabled || false);
        d.ismac = /^mac$/i.test(navigator.platform);
        d.isios = (d.cantouch && /iphone|ipad|ipod/i.test(navigator.platform));
        d.isios4 = ((d.isios) && !("seal" in Object));
        d.isandroid = (/android/i.test(navigator.userAgent));
        d.trstyle = false;
        d.hastransform = false;
        d.hastranslate3d = false;
        d.transitionstyle = false;
        d.hastransition = false;
        d.transitionend = false;
        var e = ['transform', 'msTransform', 'webkitTransform', 'MozTransform', 'OTransform'];
        for (var a = 0; a < e.length; a++) {
            if (typeof c.style[e[a]] != "undefined") {
                d.trstyle = e[a];
                break
            }
        }
        d.hastransform = (d.trstyle != false);
        if (d.hastransform) {
            c.style[d.trstyle] = "translate3d(1px,2px,3px)";
            d.hastranslate3d = /translate3d/.test(c.style[d.trstyle])
        }
        d.transitionstyle = false;
        d.prefixstyle = '';
        d.transitionend = false;
        var e = ['transition', 'webkitTransition', 'MozTransition', 'OTransition', 'OTransition', 'msTransition', 'KhtmlTransition'];
        var f = ['', '-webkit-', '-moz-', '-o-', '-o', '-ms-', '-khtml-'];
        var g = ['transitionend', 'webkitTransitionEnd', 'transitionend', 'otransitionend', 'oTransitionEnd', 'msTransitionEnd', 'KhtmlTransitionEnd'];
        for (var a = 0; a < e.length; a++) {
            if (e[a] in c.style) {
                d.transitionstyle = e[a];
                d.prefixstyle = f[a];
                d.transitionend = g[a];
                break
            }
        }
        d.hastransition = (d.transitionstyle);

        function detectCursorGrab() {
            var b = ['-moz-grab', '-webkit-grab', 'grab'];
            if ((d.ischrome && !d.ischrome22) || d.isie) b = [];
            for (var a = 0; a < b.length; a++) {
                var p = b[a];
                c.style['cursor'] = p;
                if (c.style['cursor'] == p) return p
            }
            return 'url(http://www.google.com/intl/en_ALL/mapfiles/openhand.cur),n-resize'
        }
        d.cursorgrabvalue = detectCursorGrab();
        d.hasmousecapture = ("setCapture" in c);
        c = null;
        O = d;
        return d
    };
    var Q = function (A, B) {
        var C = this;
        this.version = '3.1.4';
        this.name = 'nicescroll';
        C.me = B;
        this.opt = {
            doc: $("body"),
            win: false,
            zindex: 0,
            cursoropacitymin: 0,
            cursoropacitymax: 1,
            cursorcolor: "#424242",
            cursorwidth: "5px",
            cursorborder: "1px solid #fff",
            cursorborderradius: "5px",
            scrollspeed: 60,
            mousescrollstep: 8 * 3,
            touchbehavior: false,
            hwacceleration: true,
            usetransition: true,
            boxzoom: false,
            dblclickzoom: true,
            gesturezoom: true,
            grabcursorenabled: true,
            autohidemode: true,
            background: "",
            iframeautoresize: true,
            cursorminheight: 32,
            preservenativescrolling: true,
            railoffset: false,
            bouncescroll: true,
            spacebarenabled: true,
            railpadding: {
                top: 0,
                right: 0,
                left: 0,
                bottom: 0
            },
            disableoutline: true,
            horizrailenabled: true,
            railalign: "right",
            railvalign: "bottom",
            enabletranslate3d: true,
            enablemousewheel: true,
            enablekeyboard: true,
            smoothscroll: true,
            sensitiverail: true,
            enablemouselockapi: true,
            cursorfixedheight: false
        };
        this.opt.snapbackspeed = 80;
        if (A || false) {
            for (var a in C.opt) {
                if (typeof A[a] != "undefined") C.opt[a] = A[a]
            }
        }
        this.doc = C.opt.doc;
        this.iddoc = (this.doc && this.doc[0]) ? this.doc[0].id || '' : '';
        this.ispage = /BODY|HTML/.test((C.opt.win) ? C.opt.win[0].nodeName : this.doc[0].nodeName);
        this.haswrapper = (C.opt.win !== false);
        this.win = C.opt.win || (this.ispage ? $(window) : this.doc);
        this.docscroll = (this.ispage && !this.haswrapper) ? $(window) : this.win;
        this.body = $("body");
        this.viewport = false;
        this.isfixed = false;
        this.iframe = false;
        this.isiframe = ((this.doc[0].nodeName == 'IFRAME') && (this.win[0].nodeName == 'IFRAME'));
        this.istextarea = (this.win[0].nodeName == 'TEXTAREA');
        this.forcescreen = false;
        this.canshowonmouseevent = (C.opt.autohidemode != "scroll");
        this.onmousedown = false;
        this.onmouseup = false;
        this.onmousemove = false;
        this.onmousewheel = false;
        this.onkeypress = false;
        this.ongesturezoom = false;
        this.onclick = false;
        this.onscrollstart = false;
        this.onscrollend = false;
        this.onscrollcancel = false;
        this.onzoomin = false;
        this.onzoomout = false;
        this.view = false;
        this.page = false;
        this.scroll = {
            x: 0,
            y: 0
        };
        this.scrollratio = {
            x: 0,
            y: 0
        };
        this.cursorheight = 20;
        this.scrollvaluemax = 0;
        this.scrollrunning = false;
        this.scrollmom = false;
        this.observer = false;
        do {
            this.id = "ascrail" + (K++)
        } while (document.getElementById(this.id));
        this.rail = false;
        this.cursor = false;
        this.cursorfreezed = false;
        this.zoom = false;
        this.zoomactive = false;
        this.hasfocus = false;
        this.hasmousefocus = false;
        this.visibility = true;
        this.locked = false;
        this.hidden = false;
        this.cursoractive = true;
        this.nativescrollingarea = false;
        this.events = [];
        this.saved = {};
        this.delaylist = {};
        this.synclist = {};
        this.lastdeltax = 0;
        this.lastdeltay = 0;
        this.detected = P();
        var D = $.extend({}, this.detected);
        this.canhwscroll = (D.hastransform && C.opt.hwacceleration);
        this.ishwscroll = (this.canhwscroll && C.haswrapper);
        this.istouchcapable = false;
        if (D.cantouch && D.ischrome && !D.isios && !D.isandroid) {
            this.istouchcapable = true;
            D.cantouch = false
        }
        if (D.cantouch && D.ismozilla && !D.isios) {
            this.istouchcapable = true;
            D.cantouch = false
        }
        if (!C.opt.enablemouselockapi) {
            D.hasmousecapture = false;
            D.haspointerlock = false
        }
        this.delayed = function (a, b, c, d) {
            var e = C.delaylist[a];
            var f = (new Date()).getTime();
            if (!d && e && e.tt) return false;
            if (e && e.tt) clearTimeout(e.tt);
            if (e && e.last + c > f && !e.tt) {
                C.delaylist[a] = {
                    last: f + c,
                    tt: setTimeout(function () {
                        C.delaylist[a].tt = 0;
                        b.call()
                    }, c)
                }
            } else if (!e || !e.tt) {
                C.delaylist[a] = {
                    last: f,
                    tt: 0
                };
                setTimeout(function () {
                    b.call()
                }, 0)
            }
        };
        this.synched = function (b, c) {
            function requestSync() {
                if (C.onsync) return;
                M(function () {
                    C.onsync = false;
                    for (b in C.synclist) {
                        var a = C.synclist[b];
                        if (a) a.call(C);
                        C.synclist[b] = false
                    }
                });
                C.onsync = true
            };
            C.synclist[b] = c;
            requestSync();
            return b
        };
        this.unsynched = function (a) {
            if (C.synclist[a]) C.synclist[a] = false
        };
        this.css = function (a, b) {
            for (var n in b) {
                C.saved.css.push([a, n, a.css(n)]);
                a.css(n, b[n])
            }
        };
        this.scrollTop = function (a) {
            return (typeof a == "undefined") ? C.getScrollTop() : C.setScrollTop(a)
        };
        this.scrollLeft = function (a) {
            return (typeof a == "undefined") ? C.getScrollLeft() : C.setScrollLeft(a)
        };
        BezierClass = function (a, b, c, d, e, f, g) {
            this.st = a;
            this.ed = b;
            this.spd = c;
            this.p1 = d || 0;
            this.p2 = e || 1;
            this.p3 = f || 0;
            this.p4 = g || 1;
            this.ts = (new Date()).getTime();
            this.df = this.ed - this.st
        };
        BezierClass.prototype = {
            B2: function (t) {
                return 3 * t * t * (1 - t)
            },
            B3: function (t) {
                return 3 * t * (1 - t) * (1 - t)
            },
            B4: function (t) {
                return (1 - t) * (1 - t) * (1 - t)
            },
            getNow: function () {
                var a = (new Date()).getTime();
                var b = 1 - ((a - this.ts) / this.spd);
                var c = this.B2(b) + this.B3(b) + this.B4(b);
                return (b < 0) ? this.ed : this.st + Math.round(this.df * c)
            },
            update: function (a, b) {
                this.st = this.getNow();
                this.ed = a;
                this.spd = b;
                this.ts = (new Date()).getTime();
                this.df = this.ed - this.st;
                return this
            }
        };
        if (this.ishwscroll) {
            this.doc.translate = {
                x: 0,
                y: 0,
                tx: "0px",
                ty: "0px"
            };
            if (D.hastranslate3d && D.isios) this.doc.css("-webkit-backface-visibility", "hidden");

            function getMatrixValues() {
                var a = C.doc.css(D.trstyle);
                if (a && (a.substr(0, 6) == "matrix")) {
                    return a.replace(/^.*\((.*)\)$/g, "$1").replace(/px/g, '').split(/, +/)
                }
                return false
            }
            this.getScrollTop = function (a) {
                if (!a) {
                    var b = getMatrixValues();
                    if (b) return (b.length == 16) ? -b[13] : -b[5];
                    if (C.timerscroll && C.timerscroll.bz) return C.timerscroll.bz.getNow()
                }
                return C.doc.translate.y
            };
            this.getScrollLeft = function (a) {
                if (!a) {
                    var b = getMatrixValues();
                    if (b) return (b.length == 16) ? -b[12] : -b[4];
                    if (C.timerscroll && C.timerscroll.bh) return C.timerscroll.bh.getNow()
                }
                return C.doc.translate.x
            };
            if (document.createEvent) {
                this.notifyScrollEvent = function (a) {
                    var e = document.createEvent("UIEvents");
                    e.initUIEvent("scroll", false, true, window, 1);
                    a.dispatchEvent(e)
                }
            } else if (document.fireEvent) {
                this.notifyScrollEvent = function (a) {
                    var e = document.createEventObject();
                    a.fireEvent("onscroll");
                    e.cancelBubble = true
                }
            } else {
                this.notifyScrollEvent = function (a, b) {}
            }
            if (D.hastranslate3d && C.opt.enabletranslate3d) {
                this.setScrollTop = function (a, b) {
                    C.doc.translate.y = a;
                    C.doc.translate.ty = (a * -1) + "px";
                    C.doc.css(D.trstyle, "translate3d(" + C.doc.translate.tx + "," + C.doc.translate.ty + ",0px)");
                    if (!b) C.notifyScrollEvent(C.win[0])
                };
                this.setScrollLeft = function (a, b) {
                    C.doc.translate.x = a;
                    C.doc.translate.tx = (a * -1) + "px";
                    C.doc.css(D.trstyle, "translate3d(" + C.doc.translate.tx + "," + C.doc.translate.ty + ",0px)");
                    if (!b) C.notifyScrollEvent(C.win[0])
                }
            } else {
                this.setScrollTop = function (a, b) {
                    C.doc.translate.y = a;
                    C.doc.translate.ty = (a * -1) + "px";
                    C.doc.css(D.trstyle, "translate(" + C.doc.translate.tx + "," + C.doc.translate.ty + ")");
                    if (!b) C.notifyScrollEvent(C.win[0])
                };
                this.setScrollLeft = function (a, b) {
                    C.doc.translate.x = a;
                    C.doc.translate.tx = (a * -1) + "px";
                    C.doc.css(D.trstyle, "translate(" + C.doc.translate.tx + "," + C.doc.translate.ty + ")");
                    if (!b) C.notifyScrollEvent(C.win[0])
                }
            }
        } else {
            this.getScrollTop = function () {
                return C.docscroll.scrollTop()
            };
            this.setScrollTop = function (a) {
                return C.docscroll.scrollTop(a)
            };
            this.getScrollLeft = function () {
                return C.docscroll.scrollLeft()
            };
            this.setScrollLeft = function (a) {
                return C.docscroll.scrollLeft(a)
            }
        }
        this.getTarget = function (e) {
            if (!e) return false;
            if (e.target) return e.target;
            if (e.srcElement) return e.srcElement;
            return false
        };
        this.hasParent = function (e, a) {
            if (!e) return false;
            var b = e.target || e.srcElement || e || false;
            while (b && b.id != a) {
                b = b.parentNode || false
            }
            return (b !== false)
        };
        var E = {
            "thin": 1,
            "medium": 3,
            "thick": 5
        };

        function getWidthToPixel(a, b, c) {
            var d = a.css(b);
            var e = parseFloat(d);
            if (isNaN(e)) {
                e = E[d] || 0;
                var f = (e == 3) ? ((c) ? (C.win.outerHeight() - C.win.innerHeight()) : (C.win.outerWidth() - C.win.innerWidth())) : 1;
                if (C.isie8 && e) e += 1;
                return (f) ? e : 0
            }
            return e
        };
        this.getOffset = function () {
            if (C.isfixed) return {
                top: parseFloat(C.win.css('top')),
                left: parseFloat(C.win.css('left'))
            };
            if (!C.viewport) return C.win.offset();
            var a = C.win.offset();
            var b = C.viewport.offset();
            return {
                top: a.top - b.top + C.viewport.scrollTop(),
                left: a.left - b.left + C.viewport.scrollLeft()
            }
        };
        this.updateScrollBar = function (a) {
            if (C.ishwscroll) {
                C.rail.css({
                    height: C.win.innerHeight()
                });
                if (C.railh) C.railh.css({
                    width: C.win.innerWidth()
                })
            } else {
                var b = C.getOffset();
                var c = {
                    top: b.top,
                    left: b.left
                };
                c.top += getWidthToPixel(C.win, 'border-top-width', true);
                var d = (C.win.outerWidth() - C.win.innerWidth()) / 2;
                c.left += (C.rail.align) ? C.win.outerWidth() - getWidthToPixel(C.win, 'border-right-width') - C.rail.width : getWidthToPixel(C.win, 'border-left-width');
                var e = C.opt.railoffset;
                if (e) {
                    if (e.top) c.top += e.top;
                    if (C.rail.align && e.left) c.left += e.left
                }
                if (!C.locked) C.rail.css({
                    top: c.top,
                    left: c.left,
                    height: (a) ? a.h : C.win.innerHeight()
                });
                if (C.zoom) {
                    C.zoom.css({
                        top: c.top + 1,
                        left: (C.rail.align == 1) ? c.left - 20 : c.left + C.rail.width + 4
                    })
                }
                if (C.railh && !C.locked) {
                    var c = {
                        top: b.top,
                        left: b.left
                    };
                    var y = (C.railh.align) ? c.top + getWidthToPixel(C.win, 'border-top-width', true) + C.win.innerHeight() - C.railh.height : c.top + getWidthToPixel(C.win, 'border-top-width', true);
                    var x = c.left + getWidthToPixel(C.win, 'border-left-width');
                    C.railh.css({
                        top: y,
                        left: x,
                        width: C.railh.width
                    })
                }
            }
        };
        this.doRailClick = function (e, a, b) {
            var c, pg, cur, pos;
            if (C.rail.drag && C.rail.drag.pt != 1) return;
            if (C.locked) return;
            if (C.rail.drag) return;
            C.cancelScroll();
            C.cancelEvent(e);
            if (a) {
                c = (b) ? C.doScrollLeft : C.doScrollTop;
                cur = (b) ? ((e.pageX - C.railh.offset().left - (C.cursorwidth / 2)) * C.scrollratio.x) : ((e.pageY - C.rail.offset().top - (C.cursorheight / 2)) * C.scrollratio.y);
                c(cur)
            } else {
                c = (b) ? C.doScrollLeftBy : C.doScrollBy;
                cur = (b) ? C.scroll.x : C.scroll.y;
                pos = (b) ? e.pageX - C.railh.offset().left : e.pageY - C.rail.offset().top;
                pg = (b) ? C.view.w : C.view.h;
                (cur >= pos) ? c(pg): c(-pg)
            }
        };
        C.hasanimationframe = (M);
        C.hascancelanimationframe = (N);
        if (!C.hasanimationframe) {
            M = function (a) {
                return setTimeout(a, 16)
            };
            N = clearInterval
        } else if (!C.hascancelanimationframe) N = function () {
            C.cancelAnimationFrame = true
        };
        this.init = function () {
            C.saved.css = [];
            if (D.isie7mobile) return true;
            if (D.hasmstouch) C.css((C.ispage) ? $("html") : C.win, {
                '-ms-touch-action': 'none'
            });
            if (!C.ispage || (!D.cantouch && !D.isieold && !D.isie9mobile)) {
                var m = C.docscroll;
                if (C.ispage) m = (C.haswrapper) ? C.win : C.doc;
                if (!D.isie9mobile) C.css(m, {
                    'overflow-y': 'hidden'
                });
                if (C.ispage && D.isie7) {
                    if (C.doc[0].nodeName == 'BODY') C.css($("html"), {
                        'overflow-y': 'hidden'
                    });
                    else if (C.doc[0].nodeName == 'HTML') C.css($("body"), {
                        'overflow-y': 'hidden'
                    })
                }
                if (D.isios && !C.ispage && !C.haswrapper) C.css($("body"), {
                    "-webkit-overflow-scrolling": "touch"
                });
                var o = $(document.createElement('div'));
                o.css({
                    position: "relative",
                    top: 0,
                    "float": "right",
                    width: C.opt.cursorwidth,
                    height: "0px",
                    'background-color': C.opt.cursorcolor,
                    border: C.opt.cursorborder,
                    'background-clip': 'padding-box',
                    '-webkit-border-radius': C.opt.cursorborderradius,
                    '-moz-border-radius': C.opt.cursorborderradius,
                    'border-radius': C.opt.cursorborderradius
                });
                o.hborder = parseFloat(o.outerHeight() - o.innerHeight());
                C.cursor = o;
                var p = $(document.createElement('div'));
                p.attr('id', C.id);
                var v, a, kp = ["left", "right"];
                for (var n in kp) {
                    a = kp[n];
                    v = C.opt.railpadding[a];
                    (v) ? p.css("padding-" + a, v + "px"): C.opt.railpadding[a] = 0
                }
                p.append(o);
                p.width = Math.max(parseFloat(C.opt.cursorwidth), o.outerWidth()) + C.opt.railpadding['left'] + C.opt.railpadding['right'];
                p.css({
                    width: p.width + "px",
                    'zIndex': (C.ispage) ? C.opt.zindex : C.opt.zindex + 2,
                    "background": C.opt.background
                });
                p.visibility = true;
                p.scrollable = true;
                p.align = (C.opt.railalign == "left") ? 0 : 1;
                C.rail = p;
                C.rail.drag = false;
                var q = false;
                if (C.opt.boxzoom && !C.ispage && !D.isieold) {
                    q = document.createElement('div');
                    C.bind(q, "click", C.doZoom);
                    C.zoom = $(q);
                    C.zoom.css({
                        "cursor": "pointer",
                        'z-index': C.opt.zindex,
                        'backgroundImage': 'url(' + L + 'zoomico_wev8.png)',
                        'height': 18,
                        'width': 18,
                        'backgroundPosition': '0px 0px'
                    });
                    if (C.opt.dblclickzoom) C.bind(C.win, "dblclick", C.doZoom);
                    if (D.cantouch && C.opt.gesturezoom) {
                        C.ongesturezoom = function (e) {
                            if (e.scale > 1.5) C.doZoomIn(e);
                            if (e.scale < 0.8) C.doZoomOut(e);
                            return C.cancelEvent(e)
                        };
                        C.bind(C.win, "gestureend", C.ongesturezoom)
                    }
                };
                C.railh = false;
                if (C.opt.horizrailenabled) {
                    C.css(m, {
                        'overflow-x': 'hidden'
                    });
                    var o = $(document.createElement('div'));
                    o.css({
                        position: "relative",
                        top: 0,
                        height: C.opt.cursorwidth,
                        width: "0px",
                        'background-color': C.opt.cursorcolor,
                        border: C.opt.cursorborder,
                        'background-clip': 'padding-box',
                        '-webkit-border-radius': C.opt.cursorborderradius,
                        '-moz-border-radius': C.opt.cursorborderradius,
                        'border-radius': C.opt.cursorborderradius
                    });
                    o.wborder = parseFloat(o.outerWidth() - o.innerWidth());
                    C.cursorh = o;
                    var r = $(document.createElement('div'));
                    r.attr('id', C.id + '-hr');
                    r.height = 1 + Math.max(parseFloat(C.opt.cursorwidth), o.outerHeight());
                    r.css({
                        height: r.height + "px",
                        'zIndex': (C.ispage) ? C.opt.zindex : C.opt.zindex + 2,
                        "background": C.opt.background
                    });
                    r.append(o);
                    r.visibility = true;
                    r.scrollable = true;
                    r.align = (C.opt.railvalign == "top") ? 0 : 1;
                    C.railh = r;
                    C.railh.drag = false
                }
                if (C.ispage) {
                    p.css({
                        position: "fixed",
                        top: "0px",
                        height: "100%"
                    });
                    (p.align) ? p.css({
                        right: "0px"
                    }): p.css({
                        left: "0px"
                    });
                    C.body.append(p);
                    if (C.railh) {
                        r.css({
                            position: "fixed",
                            left: "0px",
                            width: "100%"
                        });
                        (r.align) ? r.css({
                            bottom: "0px"
                        }): r.css({
                            top: "0px"
                        });
                        C.body.append(r)
                    }
                } else {
                    if (C.ishwscroll) {
                        if (C.win.css('position') == 'static') C.css(C.win, {
                            'position': 'relative'
                        });
                        var s = (C.win[0].nodeName == 'HTML') ? C.body : C.win;
                        if (C.zoom) {
                            C.zoom.css({
                                position: "absolute",
                                top: 1,
                                right: 0,
                                "margin-right": p.width + 4
                            });
                            s.append(C.zoom)
                        }
                        p.css({
                            position: "absolute",
                            top: 0
                        });
                        (p.align) ? p.css({
                            right: 0
                        }): p.css({
                            left: 0
                        });
                        s.append(p);
                        if (r) {
                            r.css({
                                position: "absolute",
                                left: 0,
                                bottom: 0
                            });
                            (r.align) ? r.css({
                                bottom: 0
                            }): r.css({
                                top: 0
                            });
                            s.append(r)
                        }
                    } else {
                        C.isfixed = (C.win.css("position") == "fixed");
                        var t = (C.isfixed) ? "fixed" : "absolute";
                        if (!C.isfixed) C.viewport = C.getViewport(C.win[0]);
                        if (C.viewport) C.body = C.viewport;
                        p.css({
                            position: t
                        });
                        if (C.zoom) C.zoom.css({
                            position: t
                        });
                        C.updateScrollBar();
                        C.body.append(p);
                        if (C.zoom) C.body.append(C.zoom);
                        if (C.railh) {
                            r.css({
                                position: t
                            });
                            C.body.append(r)
                        }
                    }
                    if (D.isios) C.css(C.win, {
                        '-webkit-tap-highlight-color': 'rgba(0,0,0,0)',
                        '-webkit-touch-callout': 'none'
                    });
                    if (D.isie && C.opt.disableoutline) C.win.attr("hideFocus", "true");
                    if (D.iswebkit && C.opt.disableoutline) C.win.css({
                        "outline": "none"
                    })
                }
                if (C.opt.autohidemode === false) {
                    C.autohidedom = false;
                    C.rail.css({
                        opacity: C.opt.cursoropacitymax
                    });
                    if (C.railh) C.railh.css({
                        opacity: C.opt.cursoropacitymax
                    })
                } else if (C.opt.autohidemode === true) {
                    C.autohidedom = $().add(C.rail);
                    if (C.railh) C.autohidedom = C.autohidedom.add(C.railh)
                } else if (C.opt.autohidemode == "scroll") {
                    C.autohidedom = $().add(C.rail);
                    if (C.railh) C.autohidedom = C.autohidedom.add(C.railh)
                } else if (C.opt.autohidemode == "cursor") {
                    C.autohidedom = $().add(C.cursor);
                    if (C.railh) C.autohidedom = C.autohidedom.add(C.railh.cursor)
                } else if (C.opt.autohidemode == "hidden") {
                    C.autohidedom = false;
                    C.hide();
                    C.locked = false
                }
                if (D.isie9mobile) {
                    C.scrollmom = new R(C);
                    C.onmangotouch = function (e) {
                        var a = C.getScrollTop();
                        var b = C.getScrollLeft();
                        if ((a == C.scrollmom.lastscrolly) && (b == C.scrollmom.lastscrollx)) return true;
                        var c = a - C.mangotouch.sy;
                        var d = b - C.mangotouch.sx;
                        var f = Math.round(Math.sqrt(Math.pow(d, 2) + Math.pow(c, 2)));
                        if (f == 0) return;
                        var g = (c < 0) ? -1 : 1;
                        var h = (d < 0) ? -1 : 1;
                        var i = +new Date();
                        if (C.mangotouch.lazy) clearTimeout(C.mangotouch.lazy);
                        if (((i - C.mangotouch.tm) > 80) || (C.mangotouch.dry != g) || (C.mangotouch.drx != h)) {
                            C.scrollmom.stop();
                            C.scrollmom.reset(b, a);
                            C.mangotouch.sy = a;
                            C.mangotouch.ly = a;
                            C.mangotouch.sx = b;
                            C.mangotouch.lx = b;
                            C.mangotouch.dry = g;
                            C.mangotouch.drx = h;
                            C.mangotouch.tm = i
                        } else {
                            C.scrollmom.stop();
                            C.scrollmom.update(C.mangotouch.sx - d, C.mangotouch.sy - c);
                            var j = i - C.mangotouch.tm;
                            C.mangotouch.tm = i;
                            var k = Math.max(Math.abs(C.mangotouch.ly - a), Math.abs(C.mangotouch.lx - b));
                            C.mangotouch.ly = a;
                            C.mangotouch.lx = b;
                            if (k > 2) {
                                C.mangotouch.lazy = setTimeout(function () {
                                    C.mangotouch.lazy = false;
                                    C.mangotouch.dry = 0;
                                    C.mangotouch.drx = 0;
                                    C.mangotouch.tm = 0;
                                    C.scrollmom.doMomentum(30)
                                }, 100)
                            }
                        }
                    };
                    var u = C.getScrollTop();
                    var w = C.getScrollLeft();
                    C.mangotouch = {
                        sy: u,
                        ly: u,
                        dry: 0,
                        sx: w,
                        lx: w,
                        drx: 0,
                        lazy: false,
                        tm: 0
                    };
                    C.bind(C.docscroll, "scroll", C.onmangotouch)
                } else {
                    if (D.cantouch || C.istouchcapable || C.opt.touchbehavior || D.hasmstouch) {
                        C.scrollmom = new R(C);
                        C.ontouchstart = function (e) {
                            if (e.pointerType && e.pointerType != 2) return false;
                            if (!C.locked) {
                                if (D.hasmstouch) {
                                    var a = (e.target) ? e.target : false;
                                    while (a) {
                                        var b = $(a).getNiceScroll();
                                        if ((b.length > 0) && (b[0].me == C.me)) break;
                                        if (b.length > 0) return false;
                                        if ((a.nodeName == 'DIV') && (a.id == C.id)) break;
                                        a = (a.parentNode) ? a.parentNode : false
                                    }
                                }
                                C.cancelScroll();
                                var a = C.getTarget(e);
                                if (a) {
                                    var c = (/INPUT/i.test(a.nodeName)) && (/range/i.test(a.type));
                                    if (c) return C.stopPropagation(e)
                                }
                                if (!("clientX" in e) && ("changedTouches" in e)) {
                                    e.clientX = e.changedTouches[0].clientX;
                                    e.clientY = e.changedTouches[0].clientY
                                }
                                if (C.forcescreen) {
                                    var d = e;
                                    var e = {
                                        "original": (e.original) ? e.original : e
                                    };
                                    e.clientX = d.screenX;
                                    e.clientY = d.screenY
                                }
                                C.rail.drag = {
                                    x: e.clientX,
                                    y: e.clientY,
                                    sx: C.scroll.x,
                                    sy: C.scroll.y,
                                    st: C.getScrollTop(),
                                    sl: C.getScrollLeft(),
                                    pt: 2
                                };
                                if (C.opt.touchbehavior && C.isiframe && D.isie) {
                                    var f = C.win.position();
                                    C.rail.drag.x += f.left;
                                    C.rail.drag.y += f.top
                                }
                                C.hasmoving = false;
                                C.lastmouseup = false;
                                C.scrollmom.reset(e.clientX, e.clientY);
                                if (!D.cantouch && !this.istouchcapable && !D.hasmstouch) {
                                    var g = (a) ? /INPUT|SELECT|TEXTAREA/i.test(a.nodeName) : false;
                                    if (!g) {
                                        if (!C.ispage && D.hasmousecapture) a.setCapture();
                                        return C.cancelEvent(e)
                                    }
                                    if (/SUBMIT|CANCEL|BUTTON/i.test($(a).attr('type'))) {
                                        pc = {
                                            "tg": a,
                                            "click": false
                                        };
                                        C.preventclick = pc
                                    }
                                }
                            }
                        };
                        C.ontouchend = function (e) {
                            if (e.pointerType && e.pointerType != 2) return false;
                            if (C.rail.drag && (C.rail.drag.pt == 2)) {
                                C.scrollmom.doMomentum();
                                C.rail.drag = false;
                                if (C.hasmoving) {
                                    C.hasmoving = false;
                                    C.lastmouseup = true;
                                    C.hideCursor();
                                    if (D.hasmousecapture) document.releaseCapture();
                                    if (!D.cantouch) return C.cancelEvent(e)
                                }
                            }
                        };
                        var x = (C.opt.touchbehavior && C.isiframe && !D.hasmousecapture);
                        C.ontouchmove = function (e, a) {
                            if (e.pointerType && e.pointerType != 2) return false;
                            if (C.rail.drag && (C.rail.drag.pt == 2)) {
                                if (D.cantouch && (typeof e.original == "undefined")) return true;
                                C.hasmoving = true;
                                if (C.preventclick && !C.preventclick.click) {
                                    C.preventclick.click = C.preventclick.tg.onclick || false;
                                    C.preventclick.tg.onclick = C.onpreventclick
                                }
                                var b = $.extend({
                                    "original": e
                                }, e);
                                e = b;
                                if (("changedTouches" in e)) {
                                    e.clientX = e.changedTouches[0].clientX;
                                    e.clientY = e.changedTouches[0].clientY
                                }
                                if (C.forcescreen) {
                                    var c = e;
                                    var e = {
                                        "original": (e.original) ? e.original : e
                                    };
                                    e.clientX = c.screenX;
                                    e.clientY = c.screenY
                                }
                                var d = ofy = 0;
                                if (x && !a) {
                                    var f = C.win.position();
                                    d = -f.left;
                                    ofy = -f.top
                                }
                                var g = e.clientY + ofy;
                                var h = (g - C.rail.drag.y);
                                var i = C.rail.drag.st - h;
                                if (C.ishwscroll && C.opt.bouncescroll) {
                                    if (i < 0) {
                                        i = Math.round(i / 2)
                                    } else if (i > C.page.maxh) {
                                        i = C.page.maxh + Math.round((i - C.page.maxh) / 2)
                                    }
                                } else {
                                    if (i < 0) {
                                        i = 0;
                                        g = 0
                                    }
                                    if (i > C.page.maxh) {
                                        i = C.page.maxh;
                                        g = 0
                                    }
                                }
                                var j = e.clientX + d;
                                if (C.railh && C.railh.scrollable) {
                                    var k = (j - C.rail.drag.x);
                                    var l = C.rail.drag.sl - k;
                                    if (C.ishwscroll && C.opt.bouncescroll) {
                                        if (l < 0) {
                                            l = Math.round(l / 2)
                                        } else if (l > C.page.maxw) {
                                            l = C.page.maxw + Math.round((l - C.page.maxw) / 2)
                                        }
                                    } else {
                                        if (l < 0) {
                                            l = 0;
                                            j = 0
                                        }
                                        if (l > C.page.maxw) {
                                            l = C.page.maxw;
                                            j = 0
                                        }
                                    }
                                }
                                C.synched("touchmove", function () {
                                    try {
                                        if (C.me.is(":visible") === false) return
                                    } catch (e) {}
                                    if (C.rail.drag && (C.rail.drag.pt == 2)) {
                                        if (C.prepareTransition) C.prepareTransition(0);
                                        if (C.rail.scrollable) C.setScrollTop(i);
                                        C.scrollmom.update(j, g);
                                        if (C.railh && C.railh.scrollable) {
                                            C.setScrollLeft(l);
                                            C.showCursor(i, l)
                                        } else {
                                            C.showCursor(i)
                                        }
                                        if (D.isie10) document.selection.clear()
                                    }
                                });
                                if (!D.ischrome && !C.istouchcapable) return C.cancelEvent(e)
                            }
                        }
                    }
                    if (D.cantouch || C.opt.touchbehavior) {
                        C.onpreventclick = function (e) {
                            if (C.preventclick) {
                                C.preventclick.tg.onclick = C.preventclick.click;
                                C.preventclick = false;
                                return C.cancelEvent(e)
                            }
                        };
                        C.onmousedown = C.ontouchstart;
                        C.onmouseup = C.ontouchend;
                        C.onclick = (D.isios) ? false : function (e) {
                            if (C.lastmouseup) {
                                C.lastmouseup = false;
                                return C.cancelEvent(e)
                            } else {
                                return true
                            }
                        };
                        C.onmousemove = C.ontouchmove;
                        if (D.cursorgrabvalue) {
                            C.css((C.ispage) ? C.doc : C.win, {
                                'cursor': D.cursorgrabvalue
                            });
                            C.css(C.rail, {
                                'cursor': D.cursorgrabvalue
                            })
                        }
                    } else {
                        C.onmousedown = function (e, a) {
                            if (C.rail.drag && C.rail.drag.pt != 1) return;
                            if (C.locked) return C.cancelEvent(e);
                            C.cancelScroll();
                            C.rail.drag = {
                                x: e.clientX,
                                y: e.clientY,
                                sx: C.scroll.x,
                                sy: C.scroll.y,
                                pt: 1,
                                hr: (!!a)
                            };
                            var b = C.getTarget(e);
                            if (!C.ispage && D.hasmousecapture) b.setCapture();
                            if (C.isiframe && !D.hasmousecapture) {
                                C.saved["csspointerevents"] = C.doc.css("pointer-events");
                                C.css(C.doc, {
                                    "pointer-events": "none"
                                })
                            }
                            return C.cancelEvent(e)
                        };
                        C.onmouseup = function (e) {
                            if (C.rail.drag) {
                                if (D.hasmousecapture) document.releaseCapture();
                                if (C.isiframe && !D.hasmousecapture) C.doc.css("pointer-events", C.saved["csspointerevents"]);
                                if (C.rail.drag.pt != 1) return;
                                C.rail.drag = false;
                                return C.cancelEvent(e)
                            }
                        };
                        C.onmousemove = function (e) {
                            if (C.rail.drag) {
                                if (C.rail.drag.pt != 1) return;
                                if (D.ischrome && e.which == 0) return C.onmouseup(e);
                                C.cursorfreezed = true;
                                if (C.rail.drag.hr) {
                                    C.scroll.x = C.rail.drag.sx + (e.clientX - C.rail.drag.x);
                                    if (C.scroll.x < 0) C.scroll.x = 0;
                                    var a = C.scrollvaluemaxw;
                                    if (C.scroll.x > a) C.scroll.x = a
                                } else {
                                    C.scroll.y = C.rail.drag.sy + (e.clientY - C.rail.drag.y);
                                    if (C.scroll.y < 0) C.scroll.y = 0;
                                    var b = C.scrollvaluemax;
                                    if (C.scroll.y > b) C.scroll.y = b
                                }
                                C.synched('mousemove', function () {
                                    try {
                                        if (C.me.is(":visible") === false) return
                                    } catch (e) {}
                                    if (C.rail.drag && (C.rail.drag.pt == 1)) {
                                        C.showCursor();
                                        if (C.rail.drag.hr) C.doScrollLeft(Math.round(C.scroll.x * C.scrollratio.x));
                                        else C.doScrollTop(Math.round(C.scroll.y * C.scrollratio.y))
                                    }
                                });
                                return C.cancelEvent(e)
                            } else {
                                C.checkarea = true
                            }
                        }
                    }
                    if (D.cantouch || C.opt.touchbehavior) {
                        C.bind(C.win, "mousedown", C.onmousedown)
                    }
                    if (D.hasmstouch) {
                        C.css(C.rail, {
                            '-ms-touch-action': 'none'
                        });
                        C.css(C.cursor, {
                            '-ms-touch-action': 'none'
                        });
                        C.bind(C.win, "MSPointerDown", C.ontouchstart);
                        C.bind(document, "MSPointerUp", C.ontouchend);
                        C.bind(document, "MSPointerMove", C.ontouchmove);
                        C.bind(C.cursor, "MSGestureHold", function (e) {
                            e.preventDefault()
                        });
                        C.bind(C.cursor, "contextmenu", function (e) {
                            e.preventDefault()
                        })
                    }
                    if (this.istouchcapable) {
                        C.bind(C.win, "touchstart", C.ontouchstart);
                        C.bind(document, "touchend", C.ontouchend);
                        C.bind(document, "touchcancel", C.ontouchend);
                        C.bind(document, "touchmove", C.ontouchmove)
                    }
                    C.bind(C.cursor, "mousedown", C.onmousedown);
                    C.bind(C.cursor, "mouseup", C.onmouseup);
                    if (C.railh) {
                        C.bind(C.cursorh, "mousedown", function (e) {
                            C.onmousedown(e, true)
                        });
                        C.bind(C.cursorh, "mouseup", function (e) {
                            if (C.rail.drag && C.rail.drag.pt == 2) return;
                            C.rail.drag = false;
                            C.hasmoving = false;
                            C.hideCursor();
                            if (D.hasmousecapture) document.releaseCapture();
                            return C.cancelEvent(e)
                        })
                    }
                    C.bind(document, "mouseup", C.onmouseup);
                    if (D.hasmousecapture) C.bind(C.win, "mouseup", C.onmouseup);
                    C.bind(document, "mousemove", C.onmousemove);
                    if (C.onclick) C.bind(document, "click", C.onclick);
                    if (!D.cantouch && !C.opt.touchbehavior) {
                        C.jqbind(C.rail, "mouseenter", function () {
                            try {
                                if (C.me.is(":visible") === false) return
                            } catch (e) {}
                            if (C.canshowonmouseevent) C.showCursor();
                            C.rail.active = true
                        });
                        C.jqbind(C.rail, "mouseleave", function () {
                            C.rail.active = false;
                            if (!C.rail.drag) C.hideCursor()
                        });
                        if (C.opt.sensitiverail) {
                            C.bind(C.rail, "click", function (e) {
                                C.doRailClick(e, false, false)
                            });
                            C.bind(C.rail, "dblclick", function (e) {
                                C.doRailClick(e, true, false)
                            });
                            C.bind(C.cursor, "click", function (e) {
                                C.cancelEvent(e)
                            });
                            C.bind(C.cursor, "dblclick", function (e) {
                                C.cancelEvent(e)
                            })
                        }
                        if (C.railh) {
                            C.jqbind(C.railh, "mouseenter", function () {
                                try {
                                    if (C.me.is(":visible") === false) return
                                } catch (e) {}
                                if (C.canshowonmouseevent) C.showCursor();
                                C.rail.active = true
                            });
                            C.jqbind(C.railh, "mouseleave", function () {
                                C.rail.active = false;
                                if (!C.rail.drag) C.hideCursor()
                            });
                            if (C.opt.sensitiverail) {
                                C.bind(C.railh, "click", function (e) {
                                    C.doRailClick(e, false, true)
                                });
                                C.bind(C.railh, "dblclick", function (e) {
                                    C.doRailClick(e, true, true)
                                });
                                C.bind(C.cursorh, "click", function (e) {
                                    C.cancelEvent(e)
                                });
                                C.bind(C.cursorh, "dblclick", function (e) {
                                    C.cancelEvent(e)
                                })
                            }
                        }
                        if (C.zoom) {
                            C.jqbind(C.zoom, "mouseenter", function () {
                                try {
                                    if (C.me.is(":visible") === false) return
                                } catch (e) {}
                                if (C.canshowonmouseevent) C.showCursor();
                                C.rail.active = true
                            });
                            C.jqbind(C.zoom, "mouseleave", function () {
                                C.rail.active = false;
                                if (!C.rail.drag) C.hideCursor()
                            })
                        }
                    }
                    if (C.opt.enablemousewheel) {
                        if (!C.isiframe) C.bind((D.isie && C.ispage) ? document : C.docscroll, "mousewheel", C.onmousewheel);
                        C.bind(C.rail, "mousewheel", C.onmousewheel);
                        if (C.railh) C.bind(C.railh, "mousewheel", C.onmousewheelhr)
                    }
                    if (!C.ispage && !D.cantouch && !(/HTML|BODY/.test(C.win[0].nodeName))) {
                        if (!C.win.attr("tabindex")) C.win.attr({
                            "tabindex": J++
                        });
                        C.jqbind(C.win, "focus", function (e) {
                            try {
                                if (C.me.is(":visible") === false) return
                            } catch (e) {}
                            G = (C.getTarget(e)).id || true;
                            C.hasfocus = true;
                            if (C.canshowonmouseevent) C.noticeCursor()
                        });
                        C.jqbind(C.win, "blur", function (e) {
                            G = false;
                            C.hasfocus = false
                        });
                        C.jqbind(C.win, "mouseenter", function (e) {
                            try {
                                if (C.me.is(":visible") === false) return
                            } catch (e) {}
                            H = (C.getTarget(e)).id || true;
                            C.hasmousefocus = true;
                            if (C.canshowonmouseevent) C.noticeCursor()
                        });
                        C.jqbind(C.win, "mouseleave", function () {
                            H = false;
                            C.hasmousefocus = false
                        })
                    }
                }
                C.onkeypress = function (e) {
                    if (C.locked && C.page.maxh == 0) return true;
                    e = (e) ? e : window.e;
                    var a = C.getTarget(e);
                    if (a && /INPUT|TEXTAREA|SELECT|OPTION/.test(a.nodeName)) {
                        var b = a.getAttribute('type') || a.type || false;
                        if ((!b) || !(/submit|button|cancel/i.tp)) return true
                    }
                    if (C.hasfocus || (C.hasmousefocus && !G) || (C.ispage && !G && !H)) {
                        var c = e.keyCode;
                        if (C.locked && c != 27) return C.cancelEvent(e);
                        var d = e.ctrlKey || false;
                        var f = e.shiftKey || false;
                        var g = false;
                        switch (c) {
                            case 38:
                            case 63233:
                                C.doScrollBy(24 * 3);
                                g = true;
                                break;
                            case 40:
                            case 63235:
                                C.doScrollBy(-24 * 3);
                                g = true;
                                break;
                            case 37:
                            case 63232:
                                if (C.railh) {
                                    (d) ? C.doScrollLeft(0): C.doScrollLeftBy(24 * 3);
                                    g = true
                                }
                                break;
                            case 39:
                            case 63234:
                                if (C.railh) {
                                    (d) ? C.doScrollLeft(C.page.maxw): C.doScrollLeftBy(-24 * 3);
                                    g = true
                                }
                                break;
                            case 33:
                            case 63276:
                                C.doScrollBy(C.view.h);
                                g = true;
                                break;
                            case 34:
                            case 63277:
                                C.doScrollBy(-C.view.h);
                                g = true;
                                break;
                            case 36:
                            case 63273:
                                (C.railh && d) ? C.doScrollPos(0, 0): C.doScrollTo(0);
                                g = true;
                                break;
                            case 35:
                            case 63275:
                                (C.railh && d) ? C.doScrollPos(C.page.maxw, C.page.maxh): C.doScrollTo(C.page.maxh);
                                g = true;
                                break;
                            case 32:
                                if (C.opt.spacebarenabled) {
                                    (f) ? C.doScrollBy(C.view.h): C.doScrollBy(-C.view.h);
                                    g = true
                                }
                                break;
                            case 27:
                                if (C.zoomactive) {
                                    C.doZoom();
                                    g = true
                                }
                                break
                        }
                        if (g) return C.cancelEvent(e)
                    }
                };
                if (C.opt.enablekeyboard) C.bind(document, (D.isopera && !D.isopera12) ? "keypress" : "keydown", C.onkeypress);
                C.bind(window, 'resize', C.resize);
                C.bind(window, 'orientationchange', C.resize);
                C.bind(window, "load", C.resize);
                if (D.ischrome && !C.ispage && !C.haswrapper) {
                    var y = C.win.attr("style");
                    var z = parseFloat(C.win.css("width")) + 1;
                    C.win.css('width', z);
                    C.synched("chromefix", function () {
                        C.win.attr("style", y)
                    })
                }
                C.onAttributeChange = function (e) {
                    C.lazyResize()
                };
                if (!C.ispage && !C.haswrapper) {
                    if ("WebKitMutationObserver" in window) {
                        C.observer = new WebKitMutationObserver(function (a) {
                            a.forEach(C.onAttributeChange)
                        });
                        C.observer.observe(C.win[0], {
                            attributes: true,
                            subtree: false
                        })
                    } else {
                        C.bind(C.win, (D.isie && !D.isie9) ? "propertychange" : "DOMAttrModified", C.onAttributeChange);
                        if (D.isie9) C.win[0].attachEvent("onpropertychange", C.onAttributeChange)
                    }
                }
                if (!C.ispage && C.opt.boxzoom) C.bind(window, "resize", C.resizeZoom);
                if (C.istextarea) C.bind(C.win, "mouseup", C.resize);
                C.resize()
            }
            if (this.doc[0].nodeName == 'IFRAME') {
                function oniframeload(e) {
                    C.iframexd = false;
                    try {
                        var b = 'contentDocument' in this ? this.contentDocument : this.contentWindow.document;
                        var a = b.domain
                    } catch (e) {
                        C.iframexd = true;
                        b = false
                    };
                    if (C.iframexd) {
                        if ("console" in window) console.log('NiceScroll error: policy restriced iframe');
                        return true
                    }
                    C.forcescreen = true;
                    if (C.isiframe) {
                        C.iframe = {
                            "doc": $(b),
                            "html": C.doc.contents().find('html')[0],
                            "body": C.doc.contents().find('body')[0]
                        };
                        C.getContentSize = function () {
                            return {
                                w: Math.max(C.iframe.html.scrollWidth, C.iframe.body.scrollWidth),
                                h: Math.max(C.iframe.html.scrollHeight, C.iframe.body.scrollHeight)
                            }
                        };
                        C.docscroll = $(C.iframe.body)
                    }
                    if (!D.isios && C.opt.iframeautoresize && !C.isiframe) {
                        C.win.scrollTop(0);
                        C.doc.height("");
                        var c = Math.max(b.getElementsByTagName('html')[0].scrollHeight, b.body.scrollHeight);
                        C.doc.height(c)
                    }
                    C.resize();
                    if (D.isie7) C.css($(C.iframe.html), {
                        'overflow-y': 'hidden'
                    });
                    C.css($(C.iframe.body), {
                        'overflow-y': 'hidden'
                    });
                    if ('contentWindow' in this) {
                        C.bind(this.contentWindow, "scroll", C.onscroll)
                    } else {
                        C.bind(b, "scroll", C.onscroll)
                    }
                    if (C.opt.enablemousewheel) {
                        C.bind(b, "mousewheel", C.onmousewheel)
                    }
                    if (C.opt.enablekeyboard) C.bind(b, (D.isopera) ? "keypress" : "keydown", C.onkeypress);
                    if (D.cantouch || C.opt.touchbehavior) {
                        C.bind(b, "mousedown", C.onmousedown);
                        C.bind(b, "mousemove", function (e) {
                            C.onmousemove(e, true)
                        });
                        if (D.cursorgrabvalue) C.css($(b.body), {
                            'cursor': D.cursorgrabvalue
                        })
                    }
                    C.bind(b, "mouseup", C.onmouseup);
                    if (C.zoom) {
                        if (C.opt.dblclickzoom) C.bind(b, 'dblclick', C.doZoom);
                        if (C.ongesturezoom) C.bind(b, "gestureend", C.ongesturezoom)
                    }
                };
                if (this.doc[0].readyState && this.doc[0].readyState == "complete") {
                    setTimeout(function () {
                        oniframeload.call(C.doc[0], false)
                    }, 500)
                }
                C.bind(this.doc, "load", oniframeload)
            }
        };
        this.showCursor = function (a, b) {
            if (C.cursortimeout) {
                clearTimeout(C.cursortimeout);
                C.cursortimeout = 0
            }
            if (!C.rail) return;
            try {
                var c = C.me.offset().top;
                if (C.me.scrollParent && C.me.scrollParent().length > 0) {
                    if (C.rail.parent().get(0).tagName != "BODY") {
                        c = c - C.me.scrollParent().offset().top;
                        c = c + C.me.scrollParent().offset().scrollTop()
                    }
                }
                if (C.rail.parent().get(0).tagName != "BODY") {
                    c = c - F(".e8_boxhead").height()
                }
                C.rail.css("top", c);
                if (!C.rail.data("__resize")) {
                    C.resize();
                    C.rail.data("__resize", true)
                }
            } catch (e) {
                if (window.console) console.log(e, "jquery.nicescroll.js#showCursor")
            }
            if (C.autohidedom) {
                C.autohidedom.stop().css({
                    opacity: C.opt.cursoropacitymax
                });
                C.cursoractive = true
            }
            if ((typeof a != "undefined") && (a !== false)) {
                C.scroll.y = Math.round(a * 1 / C.scrollratio.y)
            }
            if (typeof b != "undefined") {
                C.scroll.x = Math.round(b * 1 / C.scrollratio.x)
            }
            C.cursor.css({
                height: C.cursorheight,
                top: C.scroll.y
            });
            if (C.cursorh) {
                (!C.rail.align && C.rail.visibility) ? C.cursorh.css({
                    width: C.cursorwidth,
                    left: C.scroll.x + C.rail.width
                }): C.cursorh.css({
                    width: C.cursorwidth,
                    left: C.scroll.x
                });
                C.cursoractive = true
            }
            if (C.zoom) C.zoom.stop().css({
                opacity: C.opt.cursoropacitymax
            })
        };
        this.hideCursor = function (a) {
            if (C.cursortimeout) return;
            if (!C.rail) return;
            if (!C.autohidedom) return;
            if (a) {
                C.cursortimeout = setTimeout(function () {
                    if (!C.rail.active || !C.showonmouseevent) {
                        C.autohidedom.stop().animate({
                            opacity: C.opt.cursoropacitymin
                        });
                        if (C.zoom) C.zoom.stop().animate({
                            opacity: C.opt.cursoropacitymin
                        });
                        C.cursoractive = false
                    }
                    C.cursortimeout = 0
                }, a || 400)
            } else {
                if (!C.rail.active || !C.showonmouseevent) {
                    C.autohidedom.stop().animate({
                        opacity: C.opt.cursoropacitymin
                    });
                    if (C.zoom) C.zoom.stop().animate({
                        opacity: C.opt.cursoropacitymin
                    });
                    C.cursoractive = false
                }
                C.cursortimeout = 0
            }
        };
        this.noticeCursor = function (a, b, c) {
            C.showCursor(b, c);
            if (!C.rail.active) C.hideCursor(a)
        };
        this.getContentSize = (C.ispage) ? function () {
            return {
                w: Math.max(document.body.scrollWidth, document.documentElement.scrollWidth),
                h: Math.max(document.body.scrollHeight, document.documentElement.scrollHeight)
            }
        } : (C.haswrapper) ? function () {
            return {
                w: C.doc.outerWidth() + parseInt(C.win.css('paddingLeft')) + parseInt(C.win.css('paddingRight')),
                h: C.doc.outerHeight() + parseInt(C.win.css('paddingTop')) + parseInt(C.win.css('paddingBottom'))
            }
        } : function () {
            return {
                w: C.docscroll[0].scrollWidth,
                h: C.docscroll[0].scrollHeight
            }
        };
        this.onResize = function (e, a) {
            try {
                if (C.me.is(":visible") === false) return
            } catch (e) {}
            if (!C.win) return false;
            if (!C.haswrapper && !C.ispage) {
                if (C.win.css('display') == 'none') {
                    if (C.visibility) C.hideRail().hideRailHr();
                    return false
                } else {
                    if (!C.hidden && !C.visibility) C.showRail().showRailHr()
                }
            }
            var b = C.page.maxh;
            var c = C.page.maxw;
            var d = {
                h: C.view.h,
                w: C.view.w
            };
            C.view = {
                w: (C.ispage) ? C.win.width() : parseInt(C.win[0].clientWidth),
                h: (C.ispage) ? C.win.height() : parseInt(C.win[0].clientHeight)
            };
            C.page = (a) ? a : C.getContentSize();
            C.page.maxh = Math.max(0, C.page.h - C.view.h);
            C.page.maxw = Math.max(0, C.page.w - C.view.w);
            if ((C.page.maxh == b) && (C.page.maxw == c) && (C.view.w == d.w)) {
                if (!C.ispage) {
                    var f = C.win.offset();
                    if (C.lastposition) {
                        var g = C.lastposition;
                        if ((g.top == f.top) && (g.left == f.left)) return C
                    }
                    C.lastposition = f
                } else {
                    return C
                }
            }
            if (C.page.maxh == 0) {
                C.hideRail();
                C.scrollvaluemax = 0;
                C.scroll.y = 0;
                C.scrollratio.y = 0;
                C.cursorheight = 0;
                C.setScrollTop(0);
                C.rail.scrollable = false
            } else {
                C.rail.scrollable = true
            }
            if (C.page.maxw == 0) {
                C.hideRailHr();
                C.scrollvaluemaxw = 0;
                C.scroll.x = 0;
                C.scrollratio.x = 0;
                C.cursorwidth = 0;
                C.setScrollLeft(0);
                C.railh.scrollable = false
            } else {
                C.railh.scrollable = true
            }
            C.locked = (C.page.maxh == 0) && (C.page.maxw == 0);
            if (C.locked) {
                if (!C.ispage) C.updateScrollBar(C.view);
                return false
            }
            if (!C.hidden && !C.visibility) {
                C.showRail().showRailHr()
            } else if (!C.hidden && !C.railh.visibility) C.showRailHr();
            if (C.istextarea && C.win.css('resize') && C.win.css('resize') != 'none') C.view.h -= 20;
            if (!C.ispage) C.updateScrollBar(C.view);
            C.cursorheight = Math.min(C.view.h, Math.round(C.view.h * (C.view.h / C.page.h)));
            C.cursorheight = (C.opt.cursorfixedheight) ? C.opt.cursorfixedheight : Math.max(C.opt.cursorminheight, C.cursorheight);
            C.cursorwidth = Math.min(C.view.w, Math.round(C.view.w * (C.view.w / C.page.w)));
            C.cursorwidth = (C.opt.cursorfixedheight) ? C.opt.cursorfixedheight : Math.max(C.opt.cursorminheight, C.cursorwidth);
            C.scrollvaluemax = C.view.h - C.cursorheight - C.cursor.hborder;
            if (C.railh) {
                C.railh.width = (C.page.maxh > 0) ? (C.view.w - C.rail.width) : C.view.w;
                C.scrollvaluemaxw = C.railh.width - C.cursorwidth - C.cursorh.wborder
            }
            C.scrollratio = {
                x: (C.page.maxw / C.scrollvaluemaxw),
                y: (C.page.maxh / C.scrollvaluemax)
            };
            var h = C.getScrollTop();
            if (h > C.page.maxh) {
                C.doScrollTop(C.page.maxh)
            } else {
                C.scroll.y = Math.round(C.getScrollTop() * (1 / C.scrollratio.y));
                C.scroll.x = Math.round(C.getScrollLeft() * (1 / C.scrollratio.x));
                if (C.cursoractive) C.noticeCursor()
            }
            if (C.scroll.y && (C.getScrollTop() == 0)) C.doScrollTo(Math.floor(C.scroll.y * C.scrollratio.y));
            return C
        };
        this.resize = function () {
            C.delayed('resize', C.onResize, 30);
            return C
        };
        this.lazyResize = function () {
            C.delayed('resize', C.resize, 250)
        };
        this._bind = function (a, b, c, d) {
            C.events.push({
                e: a,
                n: b,
                f: c,
                b: d,
                q: false
            });
            if (a.addEventListener) {
                a.addEventListener(b, c, d || false)
            } else if (a.attachEvent) {
                a.attachEvent("on" + b, c)
            } else {
                a["on" + b] = c
            }
        };
        this.jqbind = function (a, b, c) {
            C.events.push({
                e: a,
                n: b,
                f: c,
                q: true
            });
            $(a).bind(b, c)
        };
        this.bind = function (b, c, d, f) {
            var g = ("jquery" in b) ? b[0] : b;
            if (g.addEventListener) {
                if (D.cantouch && /mouseup|mousedown|mousemove/.test(c)) {
                    var h = (c == 'mousedown') ? 'touchstart' : (c == 'mouseup') ? 'touchend' : 'touchmove';
                    C._bind(g, h, function (e) {
                        if (e.touches) {
                            if (e.touches.length < 2) {
                                var a = (e.touches.length) ? e.touches[0] : e;
                                a.original = e;
                                d.call(this, a)
                            }
                        } else if (e.changedTouches) {
                            var a = e.changedTouches[0];
                            a.original = e;
                            d.call(this, a)
                        }
                    }, f || false)
                }
                C._bind(g, c, d, f || false);
                if (c == 'mousewheel') C._bind(g, "DOMMouseScroll", d, f || false);
                if (D.cantouch && c == "mouseup") C._bind(g, "touchcancel", d, f || false)
            } else {
                C._bind(g, c, function (e) {
                    e = e || window.event || false;
                    if (e) {
                        if (e.srcElement) e.target = e.srcElement
                    }
                    return ((d.call(g, e) === false) || f === false) ? C.cancelEvent(e) : true
                })
            }
        };
        this._unbind = function (a, b, c, d) {
            if (a.removeEventListener) {
                a.removeEventListener(b, c, d)
            } else if (a.detachEvent) {
                a.detachEvent('on' + b, c)
            } else {
                a['on' + b] = false
            }
        };
        this.unbindAll = function () {
            for (var a = 0; a < C.events.length; a++) {
                var r = C.events[a];
                (r.q) ? r.e.unbind(r.n, r.f): C._unbind(r.e, r.n, r.f, r.b)
            }
        };
        this.cancelEvent = function (e) {
            var e = (e.original) ? e.original : (e) ? e : window.event || false;
            if (!e) return false;
            if (e.preventDefault) e.preventDefault();
            if (e.stopPropagation) e.stopPropagation();
            if (e.preventManipulation) e.preventManipulation();
            e.cancelBubble = true;
            e.cancel = true;
            e.returnValue = false;
            return false
        };
        this.stopPropagation = function (e) {
            var e = (e.original) ? e.original : (e) ? e : window.event || false;
            if (!e) return false;
            if (e.stopPropagation) return e.stopPropagation();
            if (e.cancelBubble) e.cancelBubble = true;
            return false
        };
        this.showRail = function () {
            if ((C.page.maxh != 0) && (C.ispage || C.win.css('display') != 'none')) {
                C.visibility = true;
                C.rail.visibility = true;
                C.rail.css('display', 'block')
            }
            return C
        };
        this.showRailHr = function () {
            if (!C.railh) return C;
            if ((C.page.maxw != 0) && (C.ispage || C.win.css('display') != 'none')) {
                C.railh.visibility = true;
                C.railh.css('display', 'block')
            }
            return C
        };
        this.hideRail = function () {
            C.visibility = false;
            C.rail.visibility = false;
            C.rail.css('display', 'none');
            return C
        };
        this.hideRailHr = function () {
            if (!C.railh) return C;
            C.railh.visibility = false;
            C.railh.css('display', 'none');
            return C
        };
        this.show = function () {
            C.hidden = false;
            C.locked = false;
            return C.showRail().showRailHr()
        };
        this.hide = function () {
            C.hidden = true;
            C.locked = true;
            return C.hideRail().hideRailHr()
        };
        this.toggle = function () {
            return (C.hidden) ? C.show() : C.hide()
        };
        this.remove = function () {
            C.stop();
            if (C.cursortimeout) clearTimeout(C.cursortimeout);
            C.doZoomOut();
            C.unbindAll();
            if (C.observer !== false) C.observer.disconnect();
            C.events = [];
            if (C.cursor) {
                C.cursor.remove();
                C.cursor = null
            }
            if (C.cursorh) {
                C.cursorh.remove();
                C.cursorh = null
            }
            if (C.rail) {
                C.rail.remove();
                C.rail = null
            }
            if (C.railh) {
                C.railh.remove();
                C.railh = null
            }
            if (C.zoom) {
                C.zoom.remove();
                C.zoom = null
            }
            for (var a = 0; a < C.saved.css.length; a++) {
                var d = C.saved.css[a];
                d[0].css(d[1], (typeof d[2] == "undefined") ? '' : d[2])
            }
            C.saved = false;
            C.me.data('__nicescroll', '');
            C.me = null;
            C.doc = null;
            C.docscroll = null;
            C.win = null;
            return C
        };
        this.scrollstart = function (a) {
            this.onscrollstart = a;
            return C
        };
        this.scrollend = function (a) {
            this.onscrollend = a;
            return C
        };
        this.scrollcancel = function (a) {
            this.onscrollcancel = a;
            return C
        };
        this.zoomin = function (a) {
            this.onzoomin = a;
            return C
        };
        this.zoomout = function (a) {
            this.onzoomout = a;
            return C
        };
        this.isScrollable = function (e) {
            var a = (e.target) ? e.target : e;
            while (a && (a.nodeType == 1) && !(/BODY|HTML/.test(a.nodeName))) {
                var b = $(a);
                var c = b.css('overflowY') || b.css('overflowX') || b.css('overflow') || '';
                if (/scroll|auto/.test(c)) return (a.clientHeight != a.scrollHeight);
                a = (a.parentNode) ? a.parentNode : false
            }
            return false
        };
        this.getViewport = function (a) {
            var b = (a && a.parentNode) ? a.parentNode : false;
            while (b && (b.nodeType == 1) && !(/BODY|HTML/.test(b.nodeName))) {
                var c = $(b);
                var d = c.css('overflowY') || c.css('overflowX') || c.css('overflow') || '';
                if ((/scroll|auto/.test(d)) && (b.clientHeight != b.scrollHeight)) return c;
                if (c.getNiceScroll().length > 0) return c;
                b = (b.parentNode) ? b.parentNode : false
            }
            return false
        };

        function execScrollWheel(e, b) {
            var c = 0;
            var d = 0;
            var f = 1;
            if ("wheelDeltaY" in e) {
                f = C.opt.mousescrollstep / (16 * 3);
                c = Math.floor(e.wheelDeltaX * f);
                d = Math.floor(e.wheelDeltaY * f);
                if (d < 0) d = d + 1;
                if (b && (c == 0) && d) {
                    c = d;
                    d = 0
                }
            } else {
                var g = e.detail ? e.detail * -1 : e.wheelDelta / 40;
                if (g) {
                    (b) ? c = Math.floor(g * C.opt.mousescrollstep): d = Math.floor(g * C.opt.mousescrollstep)
                }
            }
            if (c) {
                if (C.scrollmom) {
                    C.scrollmom.stop()
                }
                C.lastdeltax += c;
                C.synched("mousewheelx", function () {
                    var a = C.lastdeltax;
                    C.lastdeltax = 0;
                    if (!C.rail.drag) {
                        C.doScrollLeftBy(a)
                    }
                })
            }
            if (d) {
                if (C.scrollmom) {
                    C.scrollmom.stop()
                }
                C.lastdeltay += d;
                C.synched("mousewheely", function () {
                    var a = C.lastdeltay;
                    C.lastdeltay = 0;
                    if (!C.rail.drag) {
                        C.doScrollBy(a)
                    }
                })
            }
        };
        this.onmousewheel = function (e) {
            if (C.locked) return true;
            if (!C.rail.scrollable) {
                if (C.railh && C.railh.scrollable) {
                    return C.onmousewheelhr(e)
                } else {
                    return true
                }
            }
            if (C.opt.preservenativescrolling && C.checkarea) {
                C.checkarea = false;
                C.nativescrollingarea = C.isScrollable(e)
            }
            if (C.nativescrollingarea) return true;
            if (C.locked) return C.cancelEvent(e);
            if (C.rail.drag) return C.cancelEvent(e);
            execScrollWheel(e, false);
            return C.cancelEvent(e)
        };
        this.onmousewheelhr = function (e) {
            if (C.locked || !C.railh.scrollable) return true;
            if (C.opt.preservenativescrolling && C.checkarea) {
                C.checkarea = false;
                C.nativescrollingarea = C.isScrollable(e)
            }
            if (C.nativescrollingarea) return true;
            if (C.locked) return C.cancelEvent(e);
            if (C.rail.drag) return C.cancelEvent(e);
            execScrollWheel(e, true);
            return C.cancelEvent(e)
        };
        this.stop = function () {
            try {
                if (C.me.is(":visible") === false) return
            } catch (e) {}
            C.cancelScroll();
            if (C.scrollmon) C.scrollmon.stop();
            C.cursorfreezed = false;
            C.scroll.y = Math.round(C.getScrollTop() * (1 / C.scrollratio.y));
            C.noticeCursor();
            return C
        };
        this.getTransitionSpeed = function (a) {
            var b = Math.round(C.opt.scrollspeed * 10);
            var c = Math.min(b, Math.round((a / 20) * C.opt.scrollspeed));
            return (c > 20) ? c : 0
        };
        if (!C.opt.smoothscroll) {
            this.doScrollLeft = function (x, a) {
                var y = C.getScrollTop();
                C.doScrollPos(x, y, a)
            };
            this.doScrollTop = function (y, a) {
                var x = C.getScrollLeft();
                C.doScrollPos(x, y, a)
            };
            this.doScrollPos = function (x, y, a) {
                var b = (x > C.page.maxw) ? C.page.maxw : x;
                if (b < 0) b = 0;
                var c = (y > C.page.maxh) ? C.page.maxh : y;
                if (c < 0) c = 0;
                C.synched('scroll', function () {
                    C.setScrollTop(c);
                    C.setScrollLeft(b)
                })
            };
            this.cancelScroll = function () {}
        } else if (C.ishwscroll && D.hastransition && C.opt.usetransition) {
            this.prepareTransition = function (a, b) {
                var c = (b) ? ((a > 20) ? a : 0) : C.getTransitionSpeed(a);
                var d = (c) ? D.prefixstyle + 'transform ' + c + 'ms ease-out' : '';
                if (!C.lasttransitionstyle || C.lasttransitionstyle != d) {
                    C.lasttransitionstyle = d;
                    C.doc.css(D.transitionstyle, d)
                }
                return c
            };
            this.doScrollLeft = function (x, a) {
                var y = (C.scrollrunning) ? C.newscrolly : C.getScrollTop();
                C.doScrollPos(x, y, a)
            };
            this.doScrollTop = function (y, a) {
                var x = (C.scrollrunning) ? C.newscrollx : C.getScrollLeft();
                C.doScrollPos(x, y, a)
            };
            this.doScrollPos = function (x, y, k) {
                var l = C.getScrollTop();
                var m = C.getScrollLeft();
                if (((C.newscrolly - l) * (y - l) < 0) || ((C.newscrollx - m) * (x - m) < 0)) C.cancelScroll();
                if (C.opt.bouncescroll == false) {
                    if (y < 0) y = 0;
                    else if (y > C.page.maxh) y = C.page.maxh;
                    if (x < 0) x = 0;
                    else if (x > C.page.maxw) x = C.page.maxw
                }
                if (x == C.newscrollx && y == C.newscrolly) return false;
                C.newscrolly = y;
                C.newscrollx = x;
                C.newscrollspeed = k || false;
                if (C.timer) return false;
                C.timer = setTimeout(function () {
                    var a = C.getScrollTop();
                    var b = C.getScrollLeft();
                    var c = {};
                    c.x = x - b;
                    c.y = y - a;
                    c.px = b;
                    c.py = a;
                    var d = Math.round(Math.sqrt(Math.pow(c.x, 2) + Math.pow(c.y, 2)));
                    var f = (C.newscrollspeed) ? C.newscrollspeed : d;
                    var g = C.prepareTransition(f);
                    if (C.timerscroll && C.timerscroll.tm) clearInterval(C.timerscroll.tm);
                    if (g > 0) {
                        if (!C.scrollrunning && C.onscrollstart) {
                            var h = {
                                "type": "scrollstart",
                                "current": {
                                    "x": b,
                                    "y": a
                                },
                                "request": {
                                    "x": x,
                                    "y": y
                                },
                                "end": {
                                    "x": C.newscrollx,
                                    "y": C.newscrolly
                                },
                                "speed": g
                            };
                            C.onscrollstart.call(C, h)
                        }
                        if (D.transitionend) {
                            if (!C.scrollendtrapped) {
                                C.scrollendtrapped = true;
                                C.bind(C.doc, D.transitionend, C.onScrollEnd, false)
                            }
                        } else {
                            if (C.scrollendtrapped) clearTimeout(C.scrollendtrapped);
                            C.scrollendtrapped = setTimeout(C.onScrollEnd, g)
                        }
                        var i = a;
                        var j = b;
                        C.timerscroll = {
                            bz: new BezierClass(i, C.newscrolly, g, 0, 0, 0.58, 1),
                            bh: new BezierClass(j, C.newscrollx, g, 0, 0, 0.58, 1)
                        };
                        if (!C.cursorfreezed) C.timerscroll.tm = setInterval(function () {
                            try {
                                if (C.me.is(":visible") === false) return
                            } catch (e) {}
                            C.showCursor(C.getScrollTop(), C.getScrollLeft())
                        }, 60)
                    }
                    C.synched("doScroll-set", function () {
                        C.timer = 0;
                        if (C.scrollendtrapped) C.scrollrunning = true;
                        C.setScrollTop(C.newscrolly);
                        C.setScrollLeft(C.newscrollx);
                        if (!C.scrollendtrapped) C.onScrollEnd()
                    })
                }, 50)
            };
            this.cancelScroll = function () {
                try {
                    if (C.me.is(":visible") === false) return
                } catch (e) {}
                if (!C.scrollendtrapped) return true;
                var a = C.getScrollTop();
                var b = C.getScrollLeft();
                C.scrollrunning = false;
                if (!D.transitionend) clearTimeout(D.transitionend);
                C.scrollendtrapped = false;
                C._unbind(C.doc, D.transitionend, C.onScrollEnd);
                C.prepareTransition(0);
                C.setScrollTop(a);
                if (C.railh) C.setScrollLeft(b);
                if (C.timerscroll && C.timerscroll.tm) clearInterval(C.timerscroll.tm);
                C.timerscroll = false;
                C.cursorfreezed = false;
                C.showCursor(a, b);
                return C
            };
            this.onScrollEnd = function () {
                try {
                    if (C.me.is(":visible") === false) return
                } catch (e) {};
                if (C.scrollendtrapped) C._unbind(C.doc, D.transitionend, C.onScrollEnd);
                C.scrollendtrapped = false;
                C.prepareTransition(0);
                if (C.timerscroll && C.timerscroll.tm) clearInterval(C.timerscroll.tm);
                C.timerscroll = false;
                var a = C.getScrollTop();
                var b = C.getScrollLeft();
                C.setScrollTop(a);
                if (C.railh) C.setScrollLeft(b);
                C.noticeCursor(false, a, b);
                C.cursorfreezed = false;
                if (a < 0) a = 0;
                else if (a > C.page.maxh) a = C.page.maxh;
                if (b < 0) b = 0;
                else if (b > C.page.maxw) b = C.page.maxw;
                if ((a != C.newscrolly) || (b != C.newscrollx)) return C.doScrollPos(b, a, C.opt.snapbackspeed);
                if (C.onscrollend && C.scrollrunning) {
                    var c = {
                        "type": "scrollend",
                        "current": {
                            "x": b,
                            "y": a
                        },
                        "end": {
                            "x": C.newscrollx,
                            "y": C.newscrolly
                        }
                    };
                    C.onscrollend.call(C, c)
                }
                C.scrollrunning = false
            }
        } else {
            this.doScrollLeft = function (x) {
                var y = (C.scrollrunning) ? C.newscrolly : C.getScrollTop();
                C.doScrollPos(x, y)
            };
            this.doScrollTop = function (y) {
                var x = (C.scrollrunning) ? C.newscrollx : C.getScrollLeft();
                C.doScrollPos(x, y)
            };
            this.doScrollPos = function (x, y) {
                var y = ((typeof y == "undefined") || (y === false)) ? C.getScrollTop(true) : y;
                if ((C.timer) && (C.newscrolly == y) && (C.newscrollx == x)) return true;
                if (C.timer) N(C.timer);
                C.timer = 0;
                var g = C.getScrollTop();
                var h = C.getScrollLeft();
                if (((C.newscrolly - g) * (y - g) < 0) || ((C.newscrollx - h) * (x - h) < 0)) C.cancelScroll();
                C.newscrolly = y;
                C.newscrollx = x;
                if (!C.bouncescroll || !C.rail.visibility) {
                    if (C.newscrolly < 0) {
                        C.newscrolly = 0
                    } else if (C.newscrolly > C.page.maxh) {
                        C.newscrolly = C.page.maxh
                    }
                }
                if (!C.bouncescroll || !C.railh.visibility) {
                    if (C.newscrollx < 0) {
                        C.newscrollx = 0
                    } else if (C.newscrollx > C.page.maxw) {
                        C.newscrollx = C.page.maxw
                    }
                }
                C.dst = {};
                C.dst.x = x - h;
                C.dst.y = y - g;
                C.dst.px = h;
                C.dst.py = g;
                var i = Math.round(Math.sqrt(Math.pow(C.dst.x, 2) + Math.pow(C.dst.y, 2)));
                C.dst.ax = C.dst.x / i;
                C.dst.ay = C.dst.y / i;
                var j = 0;
                var k = i;
                if (C.dst.x == 0) {
                    j = g;
                    k = y;
                    C.dst.ay = 1;
                    C.dst.py = 0
                } else if (C.dst.y == 0) {
                    j = h;
                    k = x;
                    C.dst.ax = 1;
                    C.dst.px = 0
                }
                var l = C.getTransitionSpeed(i);
                if (l > 0) {
                    C.bzscroll = (C.bzscroll) ? C.bzscroll.update(k, l) : new BezierClass(j, k, l, 0, 1, 0, 1)
                } else {
                    C.bzscroll = false
                }
                if (C.timer) return;
                if ((g == C.page.maxh && y >= C.page.maxh) || (h == C.page.maxw && x >= C.page.maxw)) C.checkContentSize();
                var m = 1;

                function scrolling() {
                    try {
                        if (C.me.is(":visible") === false) return
                    } catch (e) {}
                    if (C.cancelAnimationFrame) return true;
                    C.scrollrunning = true;
                    m = 1 - m;
                    if (m) return (C.timer = M(scrolling) || 1);
                    var a = 0;
                    var b = sy = C.getScrollTop();
                    if (C.dst.ay) {
                        b = (C.bzscroll) ? C.dst.py + (C.bzscroll.getNow() * C.dst.ay) : C.newscrolly;
                        var c = b - sy;
                        if ((c < 0 && b < C.newscrolly) || (c > 0 && b > C.newscrolly)) b = C.newscrolly;
                        C.setScrollTop(b);
                        if (b == C.newscrolly) a = 1
                    } else {
                        a = 1
                    }
                    var d = sx = C.getScrollLeft();
                    if (C.dst.ax) {
                        d = (C.bzscroll) ? C.dst.px + (C.bzscroll.getNow() * C.dst.ax) : C.newscrollx;
                        var c = d - sx;
                        if ((c < 0 && d < C.newscrollx) || (c > 0 && d > C.newscrollx)) d = C.newscrollx;
                        C.setScrollLeft(d);
                        if (d == C.newscrollx) a += 1
                    } else {
                        a += 1
                    }
                    if (a == 2) {
                        C.timer = 0;
                        C.cursorfreezed = false;
                        C.bzscroll = false;
                        C.scrollrunning = false;
                        if (b < 0) b = 0;
                        else if (b > C.page.maxh) b = C.page.maxh;
                        if (d < 0) d = 0;
                        else if (d > C.page.maxw) d = C.page.maxw;
                        if ((d != C.newscrollx) || (b != C.newscrolly)) C.doScrollPos(d, b);
                        else {
                            if (C.onscrollend) {
                                var f = {
                                    "type": "scrollend",
                                    "current": {
                                        "x": sx,
                                        "y": sy
                                    },
                                    "end": {
                                        "x": C.newscrollx,
                                        "y": C.newscrolly
                                    }
                                };
                                C.onscrollend.call(C, f)
                            }
                        }
                    } else {
                        C.timer = M(scrolling) || 1
                    }
                };
                C.cancelAnimationFrame = false;
                C.timer = 1;
                if (C.onscrollstart && !C.scrollrunning) {
                    var n = {
                        "type": "scrollstart",
                        "current": {
                            "x": h,
                            "y": g
                        },
                        "request": {
                            "x": x,
                            "y": y
                        },
                        "end": {
                            "x": C.newscrollx,
                            "y": C.newscrolly
                        },
                        "speed": l
                    };
                    C.onscrollstart.call(C, n)
                }
                scrolling();
                if ((g == C.page.maxh && y >= g) || (h == C.page.maxw && x >= h)) C.checkContentSize();
                C.noticeCursor()
            };
            this.cancelScroll = function () {
                if (C.timer) N(C.timer);
                C.timer = 0;
                C.bzscroll = false;
                C.scrollrunning = false;
                return C
            }
        };
        this.doScrollBy = function (a, b) {
            try {
                if (C.me.is(":visible") === false) return
            } catch (e) {};
            var c = 0;
            if (b) {
                c = Math.floor((C.scroll.y - a) * C.scrollratio.y)
            } else {
                var d = (C.timer) ? C.newscrolly : C.getScrollTop(true);
                c = d - a
            }
            if (C.bouncescroll) {
                var f = Math.round(C.view.h / 2);
                if (c < -f) c = -f;
                else if (c > (C.page.maxh + f)) c = (C.page.maxh + f)
            }
            C.cursorfreezed = false;
            py = C.getScrollTop(true);
            if (c < 0 && py <= 0) return C.noticeCursor();
            else if (c > C.page.maxh && py >= C.page.maxh) {
                C.checkContentSize();
                return C.noticeCursor()
            }
            C.doScrollTop(c)
        };
        this.doScrollLeftBy = function (a, b) {
            try {
                if (C.me.is(":visible") === false) return
            } catch (e) {};
            var c = 0;
            if (b) {
                c = Math.floor((C.scroll.x - a) * C.scrollratio.x)
            } else {
                var d = (C.timer) ? C.newscrollx : C.getScrollLeft(true);
                c = d - a
            }
            if (C.bouncescroll) {
                var f = Math.round(C.view.w / 2);
                if (c < -f) c = -f;
                else if (c > (C.page.maxw + f)) c = (C.page.maxw + f)
            }
            C.cursorfreezed = false;
            px = C.getScrollLeft(true);
            if (c < 0 && px <= 0) return C.noticeCursor();
            else if (c > C.page.maxw && px >= C.page.maxw) return C.noticeCursor();
            C.doScrollLeft(c)
        };
        this.doScrollTo = function (a, b) {
            var c = (b) ? Math.round(a * C.scrollratio.y) : a;
            if (c < 0) c = 0;
            else if (c > C.page.maxh) c = C.page.maxh;
            C.cursorfreezed = false;
            C.doScrollTop(a)
        };
        this.checkContentSize = function () {
            var a = C.getContentSize();
            if ((a.h != C.page.h) || (a.w != C.page.w)) C.resize(false, a)
        };
        C.onscroll = function (e) {
            try {
                if (C.me.is(":visible") === false) return
            } catch (e) {};
            if (C.rail.drag) return;
            if (!C.cursorfreezed) {
                C.synched('scroll', function () {
                    C.scroll.y = Math.round(C.getScrollTop() * (1 / C.scrollratio.y));
                    if (C.railh) C.scroll.x = Math.round(C.getScrollLeft() * (1 / C.scrollratio.x));
                    C.noticeCursor()
                })
            }
        };
        C.bind(C.docscroll, "scroll", C.onscroll);
        this.doZoomIn = function (e) {
            if (C.zoomactive) return;
            C.zoomactive = true;
            C.zoomrestore = {
                style: {}
            };
            var b = ['position', 'top', 'left', 'zIndex', 'backgroundColor', 'marginTop', 'marginBottom', 'marginLeft', 'marginRight'];
            var c = C.win[0].style;
            for (var a in b) {
                var d = b[a];
                C.zoomrestore.style[d] = (typeof c[d] != 'undefined') ? c[d] : ''
            }
            C.zoomrestore.style.width = C.win.css('width');
            C.zoomrestore.style.height = C.win.css('height');
            C.zoomrestore.padding = {
                w: C.win.outerWidth() - C.win.width(),
                h: C.win.outerHeight() - C.win.height()
            };
            if (D.isios4) {
                C.zoomrestore.scrollTop = $(window).scrollTop();
                $(window).scrollTop(0)
            }
            C.win.css({
                "position": (D.isios4) ? "absolute" : "fixed",
                "top": 0,
                "left": 0,
                "z-index": C.opt.zindex + 100,
                "margin": "0px"
            });
            var f = C.win.css("backgroundColor");
            if (f == "" || /transparent|rgba\(0, 0, 0, 0\)|rgba\(0,0,0,0\)/.test(f)) C.win.css("backgroundColor", "#fff");
            C.rail.css({
                "z-index": C.opt.zindex + 110
            });
            C.zoom.css({
                "z-index": C.opt.zindex + 112
            });
            C.zoom.css('backgroundPosition', '0px -18px');
            C.resizeZoom();
            if (C.onzoomin) C.onzoomin.call(C);
            return C.cancelEvent(e)
        };
        this.doZoomOut = function (e) {
            if (!C.zoomactive) return;
            C.zoomactive = false;
            C.win.css("margin", "");
            C.win.css(C.zoomrestore.style);
            if (D.isios4) {
                $(window).scrollTop(C.zoomrestore.scrollTop)
            }
            C.rail.css({
                "z-index": (C.ispage) ? C.opt.zindex : C.opt.zindex + 2
            });
            C.zoom.css({
                "z-index": C.opt.zindex
            });
            C.zoomrestore = false;
            C.zoom.css('backgroundPosition', '0px 0px');
            C.onResize();
            if (C.onzoomout) C.onzoomout.call(C);
            return C.cancelEvent(e)
        };
        this.doZoom = function (e) {
            return (C.zoomactive) ? C.doZoomOut(e) : C.doZoomIn(e)
        };
        this.resizeZoom = function () {
            if (!C.zoomactive) return;
            var a = C.getScrollTop();
            C.win.css({
                width: $(window).width() - C.zoomrestore.padding.w + "px",
                height: $(window).height() - C.zoomrestore.padding.h + "px"
            });
            C.onResize();
            console.log(a);
            C.setScrollTop(Math.min(C.page.maxh, a))
        };
        this.init();
        $.nicescroll.push(this)
    };
    var R = function (r) {
        var s = this;
        this.nc = r;
        this.lastx = 0;
        this.lasty = 0;
        this.speedx = 0;
        this.speedy = 0;
        this.lasttime = 0;
        this.steptime = 0;
        this.snapx = false;
        this.snapy = false;
        this.demulx = 0;
        this.demuly = 0;
        this.lastscrollx = -1;
        this.lastscrolly = -1;
        this.chkx = 0;
        this.chky = 0;
        this.timer = 0;
        this.time = function () {
            return +new Date()
        };
        this.reset = function (a, b) {
            s.stop();
            var c = s.time();
            s.steptime = 0;
            s.lasttime = c;
            s.speedx = 0;
            s.speedy = 0;
            s.lastx = a;
            s.lasty = b;
            s.lastscrollx = -1;
            s.lastscrolly = -1
        };
        this.update = function (a, b) {
            var c = s.time();
            s.steptime = c - s.lasttime;
            s.lasttime = c;
            var d = b - s.lasty;
            var e = a - s.lastx;
            var f = s.nc.getScrollTop();
            var g = s.nc.getScrollLeft();
            var h = f + d;
            var i = g + e;
            s.snapx = (i < 0) || (i > s.nc.page.maxw);
            s.snapy = (h < 0) || (h > s.nc.page.maxh);
            s.speedx = e;
            s.speedy = d;
            s.lastx = a;
            s.lasty = b
        };
        this.stop = function () {
            s.nc.unsynched("domomentum2d");
            if (s.timer) clearTimeout(s.timer);
            s.timer = 0;
            s.lastscrollx = -1;
            s.lastscrolly = -1
        };
        this.doSnapy = function (a, b) {
            var c = false;
            if (b < 0) {
                b = 0;
                c = true
            } else if (b > s.nc.page.maxh) {
                b = s.nc.page.maxh;
                c = true
            }
            if (a < 0) {
                a = 0;
                c = true
            } else if (a > s.nc.page.maxw) {
                a = s.nc.page.maxw;
                c = true
            }
            if (c) s.nc.doScrollPos(a, b, s.nc.opt.snapbackspeed)
        };
        this.doMomentum = function (d) {
            var t = s.time();
            var l = (d) ? t + d : s.lasttime;
            var e = s.nc.getScrollLeft();
            var f = s.nc.getScrollTop();
            var g = s.nc.page.maxh;
            var h = s.nc.page.maxw;
            s.speedx = (h > 0) ? Math.min(60, s.speedx) : 0;
            s.speedy = (g > 0) ? Math.min(60, s.speedy) : 0;
            var i = l && (t - l) <= 50;
            if ((f < 0) || (f > g) || (e < 0) || (e > h)) i = false;
            var j = (s.speedy && i) ? s.speedy : false;
            var k = (s.speedx && i) ? s.speedx : false;
            if (j || k) {
                var m = Math.max(16, s.steptime);
                if (m > 50) {
                    var n = m / 50;
                    s.speedx *= n;
                    s.speedy *= n;
                    m = 50
                }
                s.demulxy = 0;
                s.lastscrollx = s.nc.getScrollLeft();
                s.chkx = s.lastscrollx;
                s.lastscrolly = s.nc.getScrollTop();
                s.chky = s.lastscrolly;
                var o = s.lastscrollx;
                var p = s.lastscrolly;
                var q = function () {
                    var c = ((s.time() - t) > 600) ? 0.04 : 0.02;
                    if (s.speedx) {
                        o = Math.floor(s.lastscrollx - (s.speedx * (1 - s.demulxy)));
                        s.lastscrollx = o;
                        if ((o < 0) || (o > h)) c = 0.10
                    }
                    if (s.speedy) {
                        p = Math.floor(s.lastscrolly - (s.speedy * (1 - s.demulxy)));
                        s.lastscrolly = p;
                        if ((p < 0) || (p > g)) c = 0.10
                    }
                    s.demulxy = Math.min(1, s.demulxy + c);
                    s.nc.synched("domomentum2d", function () {
                        if (s.speedx) {
                            var a = s.nc.getScrollLeft();
                            if (a != s.chkx) s.stop();
                            s.chkx = o;
                            s.nc.setScrollLeft(o)
                        }
                        if (s.speedy) {
                            var b = s.nc.getScrollTop();
                            if (b != s.chky) s.stop();
                            s.chky = p;
                            s.nc.setScrollTop(p)
                        }
                        if (!s.timer) {
                            s.nc.hideCursor();
                            s.doSnapy(o, p)
                        }
                    });
                    if (s.demulxy < 1) {
                        s.timer = setTimeout(q, m)
                    } else {
                        s.stop();
                        s.nc.hideCursor();
                        s.doSnapy(o, p)
                    }
                };
                q()
            } else {
                s.doSnapy(s.nc.getScrollLeft(), s.nc.getScrollTop())
            }
        }
    };
    var S = F.fn.scrollTop;
    $.cssHooks["pageYOffset"] = {
        get: function (a, b, c) {
            var d = $.data(a, '__nicescroll') || false;
            return (d && d.ishwscroll) ? d.getScrollTop() : S.call(a)
        },
        set: function (a, b) {
            var c = $.data(a, '__nicescroll') || false;
            (c && c.ishwscroll) ? c.setScrollTop(parseInt(b)): S.call(a, b);
            return this
        }
    };
    F.fn.scrollTop = function (b) {
        if (typeof b == "undefined") {
            var c = (this[0]) ? $.data(this[0], '__nicescroll') || false : false;
            return (c && c.ishwscroll) ? c.getScrollTop() : S.call(this)
        } else {
            return this.each(function () {
                var a = $.data(this, '__nicescroll') || false;
                (a && a.ishwscroll) ? a.setScrollTop(parseInt(b)): S.call($(this), b)
            })
        }
    };
    var T = F.fn.scrollLeft;
    $.cssHooks.pageXOffset = {
        get: function (a, b, c) {
            var d = $.data(a, '__nicescroll') || false;
            return (d && d.ishwscroll) ? d.getScrollLeft() : T.call(a)
        },
        set: function (a, b) {
            var c = $.data(a, '__nicescroll') || false;
            (c && c.ishwscroll) ? c.setScrollLeft(parseInt(b)): T.call(a, b);
            return this
        }
    };
    F.fn.scrollLeft = function (b) {
        if (typeof b == "undefined") {
            var c = (this[0]) ? $.data(this[0], '__nicescroll') || false : false;
            return (c && c.ishwscroll) ? c.getScrollLeft() : T.call(this)
        } else {
            return this.each(function () {
                var a = $.data(this, '__nicescroll') || false;
                (a && a.ishwscroll) ? a.setScrollLeft(parseInt(b)): T.call($(this), b)
            })
        }
    };
    var U = function (c) {
        var d = this;
        this.length = 0;
        this.name = "nicescrollarray";
        this.each = function (b) {
            for (var a = 0; a < d.length; a++) b.call(d[a]);
            return d
        };
        this.push = function (a) {
            d[d.length] = a;
            d.length++
        };
        this.eq = function (a) {
            return d[a]
        };
        if (c) {
            for (a = 0; a < c.length; a++) {
                var e = $.data(c[a], '__nicescroll') || false;
                if (e) {
                    this[this.length] = e;
                    this.length++
                }
            }
        }
        return this
    };

    function mplex(b, c, d) {
        for (var a = 0; a < c.length; a++) d(b, c[a])
    };
    mplex(U.prototype, ['show', 'hide', 'toggle', 'onResize', 'resize', 'remove', 'stop', 'doScrollPos'], function (e, n) {
        e[n] = function () {
            var a = arguments;
            return this.each(function () {
                this[n].apply(this, a)
            })
        }
    });
    F.fn.getNiceScroll = function (a) {
        if (typeof a == "undefined") {
            return new U(this)
        } else {
            var b = $.data(this[a], '__nicescroll') || false;
            return b
        }
    };
    F.extend(F.expr[':'], {
        nicescroll: function (a) {
            return ($.data(a, '__nicescroll')) ? true : false
        }
    });
    $.fn.niceScroll = function (b, c) {
        if (typeof c == "undefined") {
            if ((typeof b == "object") && !("jquery" in b)) {
                c = b;
                b = false
            }
        }
        var d = new U();
        if (typeof c == "undefined") c = {};
        if (b || false) {
            c.doc = $(b);
            c.win = $(this)
        }
        var e = !("doc" in c);
        if (!e && !("win" in c)) c.win = $(this);
        this.each(function () {
            var a = $(this).data('__nicescroll') || false;
            if (!a) {
                c.doc = (e) ? $(this) : c.doc;
                a = new Q(c, $(this));
                $(this).data('__nicescroll', a)
            }
            d.push(a)
        });
        return (d.length == 1) ? d[0] : d
    };
    window.NiceScroll = {
        getjQuery: function () {
            return F
        }
    };
    if (!$.nicescroll) {
        $.nicescroll = new U()
    }
})(jQuery);
(function ($) {
    $.fn.perfectScrollbar = function (a) {
        if (a === "update" || a === "resize") {
            $(this).getNiceScroll().resize();
            return
        } else if (a === "getScrollObj") {
            return $(this).getNiceScroll()
        } else if (a === "getScrollTop") {
            var b = $(this).getNiceScroll().eq(0);
            return b.getScrollTop()
        } else if (a === "hide") {
            $(this).getNiceScroll().hide()
        } else if (a === "show") {
            $(this).getNiceScroll().show()
        } else if (a === "toggle") {
            $(this).getNiceScroll().toggle()
        } else if (a === "remove" || a === "destroy") {
            $(this).getNiceScroll().remove()
        } else if (a === "stop") {
            $(this).getNiceScroll().stop()
        } else if (a === "doScrollPos") {
            $(this).getNiceScroll().doScrollPos()
        }
        a = jQuery.extend({
            cursorwidth: 5,
            cursorborder: "none",
            cursorcolor: "#999",
            hidecursordelay: 0,
            zindex: 10001,
            horizrailenabled: false
        }, a);
        return $(this).niceScroll(a)
    }
})(jQuery);