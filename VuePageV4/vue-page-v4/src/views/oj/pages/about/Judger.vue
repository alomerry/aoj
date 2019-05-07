<template>
    <div>
        <Card style="margin: 0 50px 0 50px">
            <p class="card-title">Compiler & Judger</p>
            <div style="text-align: left;margin-left: 60px">
                <li>
                    <span class="compire-type">C ( GCC 5.4 )</span><br>
                    <pre>/usr/bin/gcc -DONLINE_JUDGE -O2 -w -fmax-errors=3 -std=c11 {src_path} -lm -o {exe_path}</pre>
                </li>
                <li>
                    <span class="compire-type">C++ ( G++ 5.4 )</span>
                    <pre>/usr/bin/g++ -DONLINE_JUDGE -O2 -w -fmax-errors=3 -std=c++14 {src_path} -lm -o {exe_path}</pre>
                </li>
                <li>
                    <span class="compire-type">Java ( OpenJDK 1.8 )</span>
                    <pre>/usr/bin/javac {src_path} -d {exe_dir} -encoding UTF8</pre>
                </li>
            </div>
        </Card>
        <Card style="margin: 20px 50px 0 50px">
            <p class="card-title">Result Explanation</p>
            <div class="error-explain" style="text-align: left;margin-left: 60px">
                <li>
                    <span class="error-info">Pending & Juding</span>: You solution will be judged soon, please wait for result.
                </li>
                <li>
                    <span class="error-info">Compile Error</span>: Failed to compile your source code. Click on the link to see compiler's output.
                </li>
                <li><span class="error-info">Accepted</span>: Congratulations. Your solution is correct.</li>
                <li><span class="error-info">Wrong Answer</span>: Your program's output doesn't match judger's answer.
                </li>
                <li>
                    <span class="error-info">Runtime Error</span>: Your program terminated abnormally. Possible reasons are: segment fault, divided by zero or exited with code other than 0.
                </li>
                <li>
                    <span class="error-info">Time Limit Exceeded</span>: The CPU time your program used has exceeded limit. Java has a triple time limit.
                </li>
                <li>
                    <span class="error-info">Memory Limit Exceeded</span>: The memory your program actually used has exceeded limit.
                </li>
                <li>
                    <span class="error-info">System Error</span>: Oops, something has gone wrong with the judger. Please report this to administrator
                </li>
            </div>
        </Card>
    </div>
</template>

<script>
    export default {
        name: "Judger"
    }
</script>

<style scoped>
    p.card-title {
        text-align: left;
        font-size: 20px;
        margin: 0 0 10px 20px;
        height: 35px;
    }
    
    li > span.compire-type {
        font-size: 17px;
    }
    
    div.error-explain > li {
        margin-bottom: 10px;
        margin-top: 10px;
    }
    
    li > span.error-info {
        font-size: 17px;
        font-weight: 600;
    }
    
    pre {
        padding: 5px 10px;
        white-space: pre-wrap;
        margin-top: 15px;
        margin-bottom: 15px;
        background: #f8f8f9;
        border: 1px dashed #e9eaec;
    }
</style>