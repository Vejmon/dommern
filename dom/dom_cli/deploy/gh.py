import typer

from os import environ
from github import Github, Auth

app = typer.Typer()
repo_name = "dommern"

@app.command()
def deploy(tag_msg: str, name: str, tag:str = "", release_msg:str = ""):
    g, repo = initialize()

    release_msg = release_msg if release_msg else tag_msg
    tag = tag if tag else get_next_tag(repo=repo)
    print(f"Creating tag {tag}, and release {name}")
    repo.create_git_tag_and_release(
        tag=tag,
        tag_message=tag_msg,
        release_name=name,
        release_message=release_msg,
        type="commit",
        object=get_latest_commit(repo=repo)
    )
    print(f"Release and tag created successfully")
    g.close()

def get_next_tag(repo):
    tags = repo.get_tags()
    latest_tag = sorted(tags, key=lambda x: x.commit.last_modified_datetime, reverse=True)[0].name
    major, minor = latest_tag.split(".")
    minor = int(minor) + 1
    return f"{major}.{minor}"

def get_latest_commit(repo):
    return repo.get_branch("master").commit.sha

def initialize():
    github_pat = environ.get("GITHUB_PAT")
    if not github_pat:
        raise RuntimeError(
            "GITHUB_PAT environment variable is not set. "
            "Please set it to a valid GitHub personal access token before running this command."
        )
    g = Github(auth=Auth.Token(github_pat))
    repo = g.get_user().get_repo(repo_name)
    return g, repo

if __name__ == "__main__":
    app()
