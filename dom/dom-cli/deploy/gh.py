import typer

from os import environ
from github import Github, Auth

app = typer.Typer()
repo_name = "dommern"
g = Github(auth=Auth.Token(environ.get("GITHUB_PAT")))
repo = g.get_user().get_repo(repo_name)


@app.command()
def deploy(tag_msg: str, name: str, tag:str = "", release_msg:str = ""):

    release_msg = release_msg if release_msg else tag_msg
    tag = tag if tag else get_next_tag()
    print(f"Creating tag {tag}, and release {name}")
    repo.create_git_tag_and_release(
        tag=tag,
        tag_message=tag_msg,
        release_name=name,
        release_message=release_msg,
        type="commit",
        object=get_latest_commit()
    )
    print(f"Release and tag created successfully")
    g.close()

if __name__ == "__main__":
    app()


def get_next_tag():

    tags = repo.get_tags()
    latest_tag = sorted(tags, key=lambda x: x.commit.last_modified_datetime, reverse=True)[0].name
    major, minor = latest_tag.split(".")
    minor = int(minor) + 1
    return f"{major}.{minor}"

def get_latest_commit():
    return repo.get_branch("master").commit.sha

