<template>
    <div>
        <Card style="margin: 0 50px 0 50px">
            <p class="card-title">Frequently Asked Questions</p>
            <div style="text-align: left;margin-left: 60px">
                <li>
                    <span class="compire-type">Where is the input and the output?</span><br>
                    <span class="faq-answer">
                        Your program shall read input from <code>stdin</code>('Standard Input') and write output to <code>stdout</code>('Standard Output'). For example,you can use <code>scanf</code> in C or <code>cin</code> in C++ to read from stdin,and use <code>printf</code> in C or <code>cout</code> in C++ to write to stdout. User programs are not allowed to read or write files, or you will get a
                    <code>Runtime Error</code>
                    </span>
                </li>
                <li>
                    <span class="compire-type">What's the meaning of the submission execution time?</span><br>
                    <span class="faq-answer">
                        The onlinejudge might test your code multiple times with different input files. If your code gives the correct answer within the time limit for each input file, the execution time displayed is the max of the time spent for each test case. Otherwise, the execution time will have no sense.
                    </span>
                </li>
                <li>
                    <span class="compire-type">How can I use C++ Int64?</span><br>
                    <span class="faq-answer">
                        You should declare as <code>long long</code> and use with cin/cout or <code>%lld</code>, using <code>__int64</code> will result in <code>Compile Error</code>.
                    </span>
                </li>
                <li>
                    <span class="compire-type">Java specifications?</span><br>
                    <span class="faq-answer">
                        All programs must begin in a static main method in a <code>Main</code> class. Do not use public classes: even <code>Main</code> must be non public to avoid compile error.Use buffered I/O to avoid time limit exceeded due to excesive flushing.
                    </span>
                </li>
                <li>
                    <span class="compire-type">About presentation error?</span><br>
                    <span class="faq-answer">
                        There is no presentation error in this oj.The judger will trim the blacks and wraps in your ouput's last line. if it's still different with the correct output, the result will be <code>Wrong Answer</code>.
                    </span>
                </li>
                <li>
                    <span class="compire-type">How to report bugs about this oj?</span><br>
                    <span class="faq-answer">
                        The onlinejudge is open source, you can open an issue in <a>Github</a>. The details(like env, version..) about a bug is required, which will help us a lot to solve the bug. Certainly, we are very pleased to merge your pull requests.
                    </span>
                </li>
            </div>
            <br><br><br>
        </Card>
    </div>
</template>

<script>
    export default {
        name: "FAQ"
    }
</script>

<style scoped>
    p.card-title {
        text-align: left;
        font-size: 20px;
        margin: 0 0 10px 20px;
        height: 35px;
    }
    
    pre {
        padding: 5px 10px;
        white-space: pre-wrap;
        margin-top: 15px;
        margin-bottom: 15px;
        background: #f8f8f9;
        border: 1px dashed #e9eaec;
    }
    
    span.faq-answer {
        padding-left: 20px;
    }
    
    li {
        padding-top: 10px;
    }
    
    span.compire-type {
        font-size: 17px;
    }
    
    span.faq-answer {
        margin-top: 5px;
    }
    
    code {
        font-size: 90%;
        padding: 2px 5px;
        margin: 0;
        background-color: rgba(27, 31, 35, .05);
        border-radius: 3px;
        line-height: 1.5;
    }
</style>