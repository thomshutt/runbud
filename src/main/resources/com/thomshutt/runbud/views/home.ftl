<#include "header.ftl">

   <div style="
        position: absolute;
        left: 0;
        margin-top: -20px;
        width: 100%;
        max-height: 480px;
        height: 60%;
        background-image: url('/assets/img/home_banner.jpg');
        display: table;
        border-bottom: 1px solid #CCC;
    ">
        <div style="display: table-cell; vertical-align: middle;">
           <h1 style="width: 100%; text-align: center; color: white; font-size: 6.5vw; line-height: 1.5em;">
               Running Is Better Together
           </h1>
        </div>
    </div>
    <div style="width: 100%; max-height: 480px; height: 60%; margin-top: 20px;">
        <!-- Spacemaker for absolute div above -->
    </div>

    <div style="width: 100%;">
        <p style="font-family: Arial,sans-serif; font-size: 40px; line-height: 40px; text-align: center; color: #11242A;">
          YOU/ME/RUN is a site for meeting people to run with
        </p>
    </div>

    <div class="blurb-row">
      <a href="/runs/create">
        <div class="home_subsec">
          <div class="home_subsec_img" style="background: url('/assets/img/create_run.jpg') no-repeat scroll 0% 40%;"/><!-- --></div>
          <h2>Create</h2>
          <h3>Let people know when and where you'll be running</h3>
        </div>
      </a>
      <a href="/runs">
        <div class="home_subsec">
          <div class="home_subsec_img" style="background: url('/assets/img/sample_map.jpg') no-repeat;"><!-- --></div>
          <h2>Find</h2>
          <h3>See who's running in your neighbourhood</h3>
        </div>
      </a>
      <a href="/runs">
        <div class="home_subsec">
          <div class="home_subsec_img" style="background: url('/assets/img/run_more.jpg') no-repeat;"><!-- --></div>
          <h2>Run</h2>
          <h3>Meet up, make friends and run more</h3>
        </div>
      </a>
    </div>

    <br /><br />

<#include "footer.ftl">
