# A Fabric Mod-Template for use with Java 17

<h3>How to use:</h3>

- Create a Repository with the Template with the button at the top-right
- Clone that Repo into Intellij ("Create Project from VCS")
- Wait until it's finished loading everything. really. ***everything!***
- In Intellij, go into the build.gradle, and scroll right to the bottom. you'll find parameters you want to edit like groupId, modId, modName etc etc.
- When you changed them to your liking (make sure modid & groupId are changed, or you will encounter issues with this), reload gradle (reload-icon at the top-right) and run the task `gradle finalSetup`, which will copy these values whereever needed. to be 100% safe, reload the project again afterwards.
- Enjoy your Mod-Project!

<h4>If afterwards you find your buildscript to be too cluttered, you can safely remove EVERYTHING involving the "finalSetup" task. It's only relevant for setting up the template-mod.</h4>

<h3>NOTE: This was the first ever thing i made in gradle (in terms of actual code), and it took around 4-6 hours, so it might be a little clusterfuck.</h3>
